provider "aws" {
  region = "eu-west-2"
}

variable "app_name" {
  type = string
  # default = "sl-triangle-2023" #remove default so we know it's working
  description = "The source bucket name of the app to deploy (sl-triangle-2023 or sl-transfer-2023)"
}

data "aws_s3_bucket" "source_bucket" {
  bucket = var.app_name
}

resource "random_id" "destination_bucket_random_suffix" {
  byte_length = 8
}

locals {
  destination_bucket_name = "${data.aws_s3_bucket.source_bucket.bucket}-${random_id.destination_bucket_random_suffix.hex}"
}

resource "aws_s3_bucket_website_configuration" "destination_bucket" {
  bucket = local.destination_bucket_name

  index_document {
    suffix = "index.html"
  }

  error_document {
    key = "index.html"
  }
}

resource "aws_s3_bucket_public_access_block" "acl_block" {
  bucket = aws_s3_bucket_website_configuration.destination_bucket.id

  block_public_acls       = false
  block_public_policy     = false
  ignore_public_acls      = false
  restrict_public_buckets = false

  depends_on = [aws_s3_bucket.artifacts]
}

resource "aws_s3_bucket_policy" "destination_bucket_policy" {
    bucket = local.destination_bucket_name
    policy = <<EOF
{
  "Version": "2012-10-17",
  "Statement": [
    {
      "Sid": "AllowS3GetPut",
      "Effect": "Allow",
      "Principal": {
        "AWS": [
          "arn:aws:iam::032044580362:root"
        ]
      },
      "Action": [
        "s3:GetObject",
        "s3:PutObject"
      ],
      "Resource": [
        "arn:aws:s3:::${local.destination_bucket_name}/*"
      ]
    },
    {
      "Sid": "AllowPublicRead",
      "Effect": "Allow",
      "Principal": "*",
      "Action": "s3:GetObject",
      "Resource": "arn:aws:s3:::${local.destination_bucket_name}/*"
    }
  ]
}
EOF

depends_on = [aws_s3_bucket_public_access_block.acl_block]
}

data "aws_s3_objects" "source_bucket_objects" {
  bucket = data.aws_s3_bucket.source_bucket.bucket
}

locals {
  object_keys = data.aws_s3_objects.source_bucket_objects.keys
}

resource "null_resource" "s3_objects" {
  provisioner "local-exec" {
    command = "aws s3 cp s3://${data.aws_s3_bucket.source_bucket.bucket} s3://${local.destination_bucket_name} --recursive"
  }
  depends_on = [aws_s3_bucket_website_configuration.destination_bucket]
}

resource "aws_s3_bucket" "artifacts" {
  bucket        = local.destination_bucket_name
  force_destroy = true
}

resource "aws_s3_object" "file" {
  bucket       = aws_s3_bucket_website_configuration.destination_bucket.id
  key          = "index.html"
  content_type = "text/html"
}

resource "null_resource" "url" {
  provisioner "local-exec" {
    command = "rm -rf url && aws s3 presign s3://${aws_s3_bucket.artifacts.bucket}/${aws_s3_object.file.key} >> url"
  }
}

data "local_file" "url" {
  depends_on = [
    null_resource.url
  ]
  filename = "${path.module}/url"
}

output "url" {
  value = data.local_file.url.content
}

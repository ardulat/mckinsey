variable "profile" {
  description = "AWS Profile"
  default = "anuar"
}

variable "region" {
  default = "us-east-2"
}

variable "key" {
  description = "Enter Key name"
  default = "anuar-key"
}

variable "sub_ids" {
  default = []
}

variable "instance-ami" {
  default = "ami-0f686bcf073842e84" # AMI of Mumbai region
}

variable "instance_type" {
  default = "t2.micro"
}


variable "cluster-name" {
  description = "Cluster Name"
  default = "anuar-cluster"
}

variable "server-name" {
  description = "Server Name"
  default = "anuar-server"
}

variable "vpc_name" {
  description = "VPC name"
  default = "anuar-vpc"
}

variable "profile" {
  description = "AWS Profile"
  default = "default"
}

variable "region" {
  default = "us-east-2"
}

variable "key" {
  description = "Enter Key name"
  default = "anuar"
}

variable "sub_ids" {
  default = []
}

variable "instance-ami" {
  default = "ami-082bb518441d3954c"
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

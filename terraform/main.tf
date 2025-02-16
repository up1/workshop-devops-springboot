# Create a web server
variable "ssh_key" {}
resource "digitalocean_droplet" "web" {
  image     = "docker-20-04"
  name      = "web"
  region    = "sgp1"
  size      = "s-2vcpu-4gb"
  ssh_keys  = [var.ssh_key]

  provisioner "remote-exec" {
    inline = [
      "set -e",
      "until [ -f /var/lib/cloud/instance/boot-finished ]; do sleep 1; done",
      "curl https://raw.githubusercontent.com/up1/workshop-devops-springboot/refs/heads/main/docker-compose.yml -O",
      "docker compose up -d",
    ]
    connection {
      host = self.ipv4_address
      private_key = file("~/.ssh/id_rsa")
    }
  }
}

# Firewall
resource "digitalocean_firewall" "web" {
  name = "app-ingress"

  droplet_ids = [digitalocean_droplet.web.id]

  # inbound_rule of ssh
  inbound_rule {
    protocol         = "tcp"
    port_range       = "22"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  inbound_rule {
    protocol         = "tcp"
    port_range       = "8080"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  inbound_rule {
    protocol         = "tcp"
    port_range       = "443"
    source_addresses = ["0.0.0.0/0", "::/0"]
  }

  outbound_rule {
    protocol         = "tcp"
    port_range       = "all"
    destination_addresses = ["0.0.0.0/0", "::/0"]
  }
}
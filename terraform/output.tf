output "droplet_output" {
  value = digitalocean_droplet.web.ipv4_address
}
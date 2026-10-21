#!/bin/bash
 
# Instala k3s apenas se ainda não estiver instalado
if ! command -v kubectl &> /dev/null; then
  echo "kubectl não encontrado, instalando k3s..."
  curl -sfL https://get.k3s.io | sh -
 
  # Aguarda o node ficar pronto
  sleep 10
  sudo kubectl wait --for=condition=ready node --all --timeout=60s
 
  # Deixa o kubectl acessível sem sudo
  mkdir -p ~/.kube
  sudo cp /etc/rancher/k3s/k3s.yaml ~/.kube/config
  sudo chown $USER:$USER ~/.kube/config
  sed -i 's/127.0.0.1/localhost/g' ~/.kube/config
 
  echo "k3s instalado com sucesso"
else
  echo "kubectl já instalado, pulando instalação"
fi
 

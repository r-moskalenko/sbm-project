#!/bin/bash
docker rmi --force sbm-discovery-service
docker build -t sbm-discovery-service ./discovery-service/

docker rmi --force sbm-gateway-service
docker build -t sbm-gateway-service ./gateway-service/

docker rmi --force sbm-inventory-service
docker build -t sbm-inventory-service ./inventory-service/

docker rmi --force sbm-notification-service
docker build -t sbm-notification-service ./notification-service/

docker rmi --force sbm-order-service
docker build -t sbm-order-service ./order-service/

docker rmi --force sbm-product-service
docker build -t sbm-product-service ./product-service/

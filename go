#!/bin/bash

set -e -u

function helptext {
    echo "Usage: ./go <command>"
    echo ""
    echo "Available commands are:"
    echo "    help              Show this help"
    echo "    prod              Build and run the production docker image"
}

function prod {
  docker-compose run mvn mvn package
  docker-compose build app
  docker-compose run app migrate
  docker-compose run --service-ports app
}

case "${1:-help}" in
    help) helptext
    ;;
    prod) prod
    ;;
    *)
      helptext
      exit 1
    ;;
esac


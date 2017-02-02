#!/bin/bash

set -e -u

function helptext {
    echo "Usage: ./go <command>"
    echo ""
    echo "Available commands are:"
    echo "    help              Show this help"
    echo "    prod              Build and run the production docker image"
    echo "    test              Run all tests within docker"
}

function d_mvn {
  docker-compose run mvn mvn "$@"
}

function prod {
  d_mvn package
  docker-compose build app
  docker-compose run app migrate
  docker-compose run --service-ports app
}

function test {
  d_mvn test
}

case "${1:-help}" in
    help) helptext
    ;;
    prod) prod
    ;;
    test) test
    ;;
    *)
      helptext
      exit 1
    ;;
esac


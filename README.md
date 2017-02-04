Todo-Backend in Java 8 and the Spark web framework
====================

This is an implementation of the [Todo-Backend API spec](https://www.todobackend.com/) built using the [Spark micro framework](http://sparkjava.com/). 
It persists todos in a Postgres database using a [JDBI](jdbi.org) data layer.

It is running live at [https://todobackend-spark.herokuapp.com/todos](https://todobackend-spark.herokuapp.com/todos). 
You can [point a todo-backend client at that live instance](https://www.todobackend.com/client/?https://todobackend-spark.herokuapp.com/todos) to play with it. You can also [run the Todo-Backend specs against that live instance](https://www.todobackend.com/specs/specs.html?https://todobackend-spark.herokuapp.com/todos) to confirm that it complies with the Todo-Backend API spec.

Developing
=======

This repo comes with a [`./go script`](https://www.thoughtworks.com/insights/blog/praise-go-script-part-i). To get started simply clone the repo and run `./go` to see how to launch the app, run tests, etc.


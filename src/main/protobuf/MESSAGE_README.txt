The messages defined here in the proto file are those which are produced/consumed to/from Kafka as the Kafka message body (value).
Either run spbc (ScalaPB compiler) separately to generate the message code/case classes or run with sbt compile.

See https://scalapb.github.io for instructions.

Also, you may have to change the module settings in the project structure to allow another source directory /target/scala-2.11/src_managed
which is where ScalaPB will put the auto-generated source.
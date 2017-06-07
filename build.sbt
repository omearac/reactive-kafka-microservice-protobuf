name := "reactive-kafka-microservice-template-protobuf"

version := "1.0"

scalaVersion := "2.11.8"

val akkaVersion = "2.4.16"

libraryDependencies ++= Seq(
    "com.typesafe.akka" %% "akka-stream-kafka"  % "0.13",
    "ch.qos.logback"    % "logback-classic"     % "1.1.3",
    "org.slf4j"         % "log4j-over-slf4j"    % "1.7.12",
    "com.typesafe.akka" %% "akka-slf4j"         % akkaVersion,
    "org.scalatest"     %% "scalatest"          % "3.0.1"       % "test",
    "com.typesafe.akka" %% "akka-testkit"       % akkaVersion   % "test",
    "com.typesafe.play" % "play-json_2.11"      % "2.4.0-M2",
    "com.typesafe.akka" %% "akka-http-core"     % "10.0.1",
    "com.typesafe.akka" %% "akka-http-testkit"  % "10.0.1",
    "io.spray"          %%  "spray-json"        % "1.3.2",
    // (optional) If you need scalapb/scalapb.proto or anything from
    // google/protobuf/*.proto
    "com.trueaccord.scalapb" %% "scalapb-runtime" % com.trueaccord.scalapb.compiler.Version.scalapbVersion % "protobuf"
)

//Run tests Sequentially
parallelExecution in Test := false

//ScalaPB Add-on
PB.targets in Compile := Seq(
    scalapb.gen() -> (sourceManaged in Compile).value
)

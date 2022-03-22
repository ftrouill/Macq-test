name := """macq-test"""
organization := "com.ftrouill"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.8"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.0.0" % Test
libraryDependencies += "org.reactivemongo" %% "play2-reactivemongo" % "0.20.13-play28"
libraryDependencies += "org.reactivemongo" %% "reactivemongo-play-json-compat" % "1.0.1-play28"
libraryDependencies += "org.reactivemongo" %% "reactivemongo-bson-compat" % "0.20.13"
libraryDependencies +=  "com.typesafe.play" %% "play-json-joda" % "2.7.4"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.ftrouill.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.ftrouill.binders._"


play.sbt.routes.RoutesKeys.routesImport += "play.modules.reactivemongo.PathBindables._"
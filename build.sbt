ThisBuild / organization := "com.example"
ThisBuild / version  := "0.1"

// the Scala version that will be used for cross-compiled libraries
ThisBuild / scalaVersion := "2.13.7"

val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val postgres = "org.postgresql" % "postgresql" % "42.3.3"

lazy val `hello-world` = (project in file("."))
  .aggregate(`hello-world-api`, `hello-world-impl`)

lazy val `hello-world-api` = (project in file("hello-world-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )

ThisBuild / lagomCassandraEnabled := false

lazy val `hello-world-impl` = (project in file("hello-world-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslPersistenceJdbc,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      evolutions,
      postgres,
      macwire,
      scalaTest
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`hello-world-api`)

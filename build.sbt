ThisBuild / organization := "io.github.wlad031"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.4"

ThisBuild / idePackagePrefix := Some("io.github.wlad031.stf")

ThisBuild / resolvers ++= Seq(
  "Typesafe".at("https://repo.typesafe.com/typesafe/releases/"),
)

ThisBuild / libraryDependencies ++= Seq(
  Seq(
    "com.chuusai" %% "shapeless" % "2.4.0-M1"
  ),
).flatten

ThisBuild / scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-Xlint:implicit-recursion",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials",
  "-language:postfixOps"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "scala-table-formatter",
    assemblyJarName in assembly := "scala-table-formatter.jar"
  )

Test / testOptions += Tests.Argument(
  framework = Some(TestFrameworks.ScalaTest),
  args = List("-oSD")
)

scalacOptions in (Compile, doc) ++= Seq(
  "-groups"
)

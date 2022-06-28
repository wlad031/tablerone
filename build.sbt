resolvers ++= Seq(
  "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  Resolver.githubPackages("wlad031")
)

libraryDependencies ++= Seq(
  "dev.vgerasimov" %% "shapelse" % "0.2.0"
)

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-Xlint:implicit-recursion",
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked"
)

lazy val root = project
  .in(file("."))
  .settings(
    name := "tablerone",
    description := "",
    version := "0.1.0",
    scalaVersion := "2.13.5",
    useScala3doc := true,
    idePackagePrefix := Some("dev.vgerasimov.tablerone"),
  )
  
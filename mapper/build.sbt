organization := "monash"

name := "Mapper"

version := "0.4.0"

scalaVersion := "2.10.4"

resolvers ++= Seq("snapshots" at "http://artifactory.its.monash.edu:8081/artifactory/repo",
"release" at "http://artifactory.its.monash.edu:8081/artifactory/repo"
)

scalacOptions ++= Seq("-deprecation", "-unchecked")

publishMavenStyle := true

credentials += Credentials("Artifactory Realm","artifactory.its.monash.edu","checkin","checkin")

publishTo := Some("monash-artifactory" at "http://artifactory.its.monash.edu:8081/artifactory/libs-release-local")

libraryDependencies ++= {
  val liftVersion = "2.6.2"
  Seq(
    "net.liftweb"       %% "lift-webkit"        % liftVersion        % "compile",
    "net.liftweb"       %% "lift-mapper"        % liftVersion        % "compile",
    "org.specs2"        %% "specs2"             % "1.13"           % "test"
  )
}

import sbt._
import Keys._

object ScalaLR extends Build {

  lazy val scalaLR: Project = Project("scala-lr", file("."),
    settings = Config.buildSettings ++ Seq(libraryDependencies ++=
      Seq(
        "org.scalanlp" %% "breeze" % "0.8.1",
        "org.scalanlp" %% "breeze-natives" % "0.10",
        "org.scalatest" % "scalatest_2.11" % "2.2.1" % "test"
      )
    ))
}

object Config {
  val buildSettings = Defaults.defaultSettings ++ Seq(
    scalaVersion := "2.11.4"
  )
}
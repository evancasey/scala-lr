package models

import java.util.Random

import breeze.linalg.{SparseVector, DenseVector}
import breeze.numerics.{round, exp}

case class DataPoint(featureVector: DenseVector[Double], label: Double)

// TODO: add defaults
case class LogisticRegression(maxIterations: Int, learningRate: Double) {

  type TrainData = Seq[DataPoint]

  val rand = new Random(42)

  def train(trainData: TrainData): LogisticRegressionModel = {

    val dim = trainData.head.featureVector.length

     // Initialize the target weight vector
     val itw = generateTargetWeights(dim)

     // Calculate our hypothesis weight vector using gradient descent
     val ftw = gradientDescent(trainData, itw, maxIterations, learningRate)

     LogisticRegressionModel(ftw)
  }

  def generateTargetWeights(dim: Int) = {
    // Scale from [0, 1] to [-1, 1]
    var itw = DenseVector.fill(dim)(2 * rand.nextDouble - 1)
    println("Initial target weights: " + itw)
    itw
  }

  // TODO: check for convergence
  def gradientDescent(trainData: TrainData, itw: DenseVector[Double], maxIterations: Int, learningRate: Double) = {
    for (i <- 1 to maxIterations) {
      println("On iteration " + i)
      val gradient: DenseVector[Double] = trainData.map { point =>
        point.featureVector * point.label * (1 / (1 + exp(itw.dot(point.featureVector) * -point.label)))
      }.reduce(_ + _)

      itw -= gradient * learningRate
    }

    println("Final w: " + itw)
    itw
  }
}

case class LogisticRegressionModel(ftw: DenseVector[Double]) {

  type TestData = Seq[DenseVector[Double]]

  def sigmoid(signal: Double) = 1 / (1 + exp(-signal))

  def predict(testData: TestData): Seq[Double] = {
    testData.map { point =>
      sigmoid(point.dot(ftw))
    }
  }
}

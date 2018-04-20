# Image Compression Using K-Means Clustering

Implemented K-Means clustering algorithm to perform color quantization for image compression without any loss of details. The size of each pixel in an image is of 3 bytes (RGB), with intensity of each color ranging from 0 to 255. The euclidian distance between two pixels is calculated using the below equation:

![alt text](https://wikimedia.org/api/rest_v1/media/math/render/svg/06cdd86ced397bbf6fad505b4c4d91fa2438b567)

# K-Means Clustering
K-means clustering is an optimization algorithm to find ?K? clusters in the given set of data points. Initially K random points from the data set are chosen as K cluster centroids. Then on basis of euclidean distance each point in the data set is assigned to a cluster and the centroids are changed to the mean of its assigned data points, this is done until convergence. There are two steps in k-means clustering algorithm:

a) Assignment step ? Each data point is assigned to the cluster whose center is nearest to it.
b) Update step ? New means (centroids) are calculated from the data points assigned to the new clusters.

## Getting Started:
Step 1:  Change the directory to the src folder
```
cd ImageCompressionUsingKMeans/src
```
Step 2:  Compile KMeans.java file
```
javac KMeans.java
```
Step 3:  Run KMeans.java using the below parameters as command line arguments <br />
- input-image: Input image path <br />
- k: K value <br /> 
- output-image: Output image path <br />
```
java Main <input-image> <k> <output-image>
```

## Output:
Compressed images of Koala.jpg and Penguins.jpg using K values 2, 5, 10, 15 and 20

## Prerequisites
Java Version 9.0.1

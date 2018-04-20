import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;


public class KMeans {
    public static void main(String [] args){
       if (args.length < 3){
            System.out.println("Usage: Kmeans <input-image> <k> <output-image>");
            return;
        }
        try{
           BufferedImage originalImage = ImageIO.read(new File("../"+args[0]));
            int k=Integer.parseInt(args[1]);
            BufferedImage kmeansJpg = kmeans_helper(originalImage,k);
            ImageIO.write(kmeansJpg, "jpg", new File("../"+args[2]));

        }catch(IOException e){
            System.out.println(e.getMessage());
        }
    }

    private static BufferedImage kmeans_helper(BufferedImage originalImage, int k){
        int w = originalImage.getWidth();
        int h = originalImage.getHeight();

        BufferedImage kmeansImage = new BufferedImage(w,h,originalImage.getType());
        Graphics2D g = kmeansImage.createGraphics();
        g.drawImage(originalImage, 0, 0, w, h, null);

        // Read rgb values from the image
        int[] rgb = new int[w*h];
        int count=0;

        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                rgb[count++]=kmeansImage.getRGB(i,j);
            }
        }

        // Call k-means algorithm: update the rgb values
        rgb = kmeans(rgb,k).clone();

        // Write the new rgb values to the image
        count=0;
        for(int i=0;i<w;i++){
            for(int j=0;j<h;j++){
                kmeansImage.setRGB(i,j,rgb[count++]);
            }
        }

        return kmeansImage;
    }

    // Your k-means code goes here
    // Update the array rgb by assigning each entry in the rgb array to its cluster center
    private static int[] kmeans(int[] rgb, int k){
        Set<Integer> kset = new HashSet<>();

        // step 1. Initializing the k centroids
        Random rand = new Random();
        int randNum;

        for (int i = 0; i<k ; i++){
            randNum = rand.nextInt(rgb.length);
            // to avoid duplicates
            while(kset.contains(rgb[randNum])){
                randNum = rand.nextInt(rgb.length);
            }
            kset.add(rgb[randNum]);
        }

        Integer[] k_centroids = kset.toArray(new Integer[0]);

        int modified_rgb[] = new int[rgb.length];
        double distance;
        int maxiterations = 100;
        int iterations = 0;
        boolean converged = false;

        while(!converged && iterations < maxiterations){
            int avgRGB[][] = new int[k][5];

            // step 2.1 Assigning data points to clusters
            for(int i=0; i<rgb.length; i++){
                double min_distance = Double.POSITIVE_INFINITY;
                int k_index = -1;

                for(int j=0; j<k; j++){
                    distance = getEuclideanDifference(rgb[i], k_centroids[j]);

                    if(Double.compare(min_distance, distance) > 0){
                        min_distance = distance;
                        k_index = j;
                    }
                }

                modified_rgb[i] = k_centroids[k_index];

                Color centroidColor = new Color(k_centroids[k_index]);
                avgRGB[k_index][0] += centroidColor.getAlpha();
                avgRGB[k_index][1] += centroidColor.getRed();
                avgRGB[k_index][2] += centroidColor.getGreen();
                avgRGB[k_index][3] += centroidColor.getBlue();
                avgRGB[k_index][4]++;
            }

            // step 2.2 Updating centroids
            Integer[] newCentroids = new Integer[k];
            for(int i=0; i<k; i++){
                int meanAlpha = avgRGB[i][0]/avgRGB[i][4];
                int meanRed = avgRGB[i][1]/avgRGB[i][4];
                int meanGreen = avgRGB[i][2]/avgRGB[i][4];
                int meanBlue = avgRGB[i][3]/avgRGB[i][4];

                newCentroids[i] = ((meanAlpha & 0x000000FF) << 24) | ((meanRed & 0x000000FF) << 16)
                        | ((meanGreen & 0x000000FF) << 8) | ((meanBlue & 0x000000FF) << 0);
            }

            converged = isConverged(k_centroids, newCentroids);
            k_centroids = newCentroids.clone();
            iterations++;
        }

        return modified_rgb;
    }

    // returns Euclidean Difference between pixel data point and centroid
    public static double getEuclideanDifference(int pixel, int centroid){
        Color pixelColor = new Color(pixel);
        Color centroidColor = new Color(centroid);

        int alpha = pixelColor.getAlpha()-centroidColor.getAlpha();
        int red = pixelColor.getRed()-centroidColor.getRed();
        int blue = pixelColor.getBlue()-centroidColor.getBlue();
        int green = pixelColor.getGreen()-centroidColor.getGreen();

        return Math.sqrt(alpha*alpha + red * red + blue * blue + green * green);
    }

    private static boolean isConverged( Integer[] oldCentroids, Integer[] newCentroids ) {
        for ( int i = 0; i < newCentroids.length; i++ )
            if ( !oldCentroids[i].equals(newCentroids[i]) )
                return false;
        return true;
    }

}

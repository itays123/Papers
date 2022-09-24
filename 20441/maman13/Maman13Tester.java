
/**
 * Tests the code of Maman 13 RGBImage class
 *
 * @author Ivgeni Dvorkin
 * @version 1.0
 */
public class Maman13Tester{
    public static void main(String[] args){
        String testPass = "\u001B[32mO.K\u001B[0m";
        String testFail = "\u001B[31mTest Failed!!!\u001B[0m";
        boolean testResult = true;
        System.out.println("Hello\n\n" +
        "This is not the greatest tester in the world.\n" +
        "This is just a tribute.\n\n"); 
        
        
        
        System.out.println("Test #1");
        System.out.print("Testing first Constructor - an all black image - ");
        RGBColor black = new RGBColor();
        RGBImage allBlack = new RGBImage(4,5);
        int indicator1 = 0;
        for(int i = 0; (i < allBlack.toRGBColorArray().length) && indicator1 != 1; i++){
            for(int j = 0; j < allBlack.toRGBColorArray()[0].length; j++){
                if ( !(allBlack.toRGBColorArray()[i][j].equals(black)) ){
                    indicator1 = 1;
                }
            }
        }
        if (indicator1 == 1){
            System.out.println(testFail);
            testResult = false;
        }
        else{
            System.out.println(testPass);
        }
        
        System.out.println("");
        System.out.println("Test #2");
        System.out.print("Testing second Constructor - Image out of given RGBColor array - ");
        RGBColor[][] arrayForSecondImage = new RGBColor[4][5];
        int indicator2 = 0;
        for(int i = 0; i < 4; i++){
            for(int j = 0; j < 5; j++){
                arrayForSecondImage[i][j]= new RGBColor();
            }
        }
        RGBImage secondImage = new RGBImage(arrayForSecondImage);
        for(int i = 0; i < secondImage.toRGBColorArray().length; i++){
            for(int j = 0; j < secondImage.toRGBColorArray()[0].length; j++){
                if ( !(secondImage.toRGBColorArray()[i][j].equals(black)) ){
                    indicator2 = 1;
                }
            }
        }
        if (indicator2 == 1){
            System.out.println(testFail);
            testResult = false;
        }
        else{
            System.out.println(testPass);
        }
        
        System.out.println("");
        System.out.println("Test #3");
        System.out.print("Testing copy Constructor - ");
        int indicator3 = 0;
        RGBImage thirdImage = new RGBImage(secondImage);
        for(int i = 0; i < secondImage.toRGBColorArray().length; i++){
            for(int j = 0; j < secondImage.toRGBColorArray()[0].length; j++){
                if ( !(thirdImage.toRGBColorArray()[i][j].equals(black)) ){
                    indicator3 = 1;
                }
            }
        }
        if (indicator3 == 1){
            System.out.println(testFail);
            testResult = false;
        }
        else{
            System.out.println(testPass);
        }
        
        System.out.println("");
        System.out.println("Test #4");
        System.out.print("Testing getHeight method - ");
        RGBImage image4 = new RGBImage(5,7);
        if (image4.getHeight() == 5){
            System.out.println(testPass);
        }
        else {
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #5");
        System.out.print("Testing getWidth method - ");
        if (image4.getWidth() == 7){
            System.out.println(testPass);
        }
        else {
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #6");
        System.out.print("Testing getPixel method - test 1/3 - ");
        RGBColor color5 = new RGBColor(5,5,5);
        RGBColor[][] arrayImage5 = new RGBColor[6][6];
        for (int i = 0; i < 6; i++){
            for (int j = 0; j < 6; j++){
                arrayImage5[i][j] = new RGBColor((i),(i),(i));
            }
        }
        RGBImage image5 = new RGBImage(arrayImage5);
        if(image5.getPixel(5,0).equals(color5)){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing getPixel method - test 2/3 - ");
        if(image5.getPixel(7,0).equals(black)){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing getPixel method - test 3/3 - ");
        if(image5.getPixel(-3,0).equals(black)){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.println("");
        System.out.println("Test #7");
        System.out.print("Testing setPixel method - test 1/3 - ");
        image5.setPixel(0,0,color5);
        if(image5.getPixel(0,0).equals(color5)){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing setPixel method - test 2/3 - ");
        image5.setPixel(8,0,color5);
        System.out.println(testPass);
        System.out.print("Testing setPixel method - test 3/3 - ");
        image5.setPixel(-8,0,color5);
        System.out.println(testPass);
        
        System.out.println("");
        System.out.println("Test #8");
        System.out.print("Testing equals method - test 1/4 - ");
        RGBImage image6 = new RGBImage(5,6);
        RGBImage image7a = new RGBImage(6,5);
        RGBImage image7b = new RGBImage(5,5);
        RGBColor[][] color8 = new RGBColor[5][6];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 6; j++){
                color8[i][j] = new RGBColor((i),(i),(i));
            }
        }
        RGBImage image8 = new RGBImage(color8);
        RGBImage image9 = new RGBImage(5,6);
        if (image6.equals(image7a)){
           System.out.println(testFail);
           testResult = false;
        }
        else{
            System.out.println(testPass);
        }
        System.out.print("Testing equals method - test 2/4 - ");
        if (image6.equals(image7b)){
           System.out.println(testFail);
           testResult = false;
        }
        else{
            System.out.println(testPass);
        }
        System.out.print("Testing equals method - test 3/4 - ");
        if (image6.equals(image8)){
           System.out.println(testFail);
           testResult = false;
        }
        else{
            System.out.println(testPass);
        }
        System.out.print("Testing equals method - test 4/4 - ");
        if (image6.equals(image9)){
           System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #9");
        System.out.print("Testing flip horizonal method - ");
        RGBColor[][] color10 = new RGBColor[5][5];
        RGBColor[][] color11 = new RGBColor[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                color10[j][i] = new RGBColor(i,i,i);
                color11[j][i] = new RGBColor(4-i,4-i,4-i);
            }
        }
        RGBImage image10 = new RGBImage(color10);
        RGBImage image11 = new RGBImage(color11);
        image11.flipHorizontal();
        if ( image10.equals(image11) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #10");
        System.out.print("Testing flip vertical method - ");
        RGBColor[][] color12 = new RGBColor[5][5];
        RGBColor[][] color13 = new RGBColor[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                color12[i][j] = new RGBColor(i,i,i);
                color13[i][j] = new RGBColor(4-i,4-i,4-i);
            }
        }
        RGBImage image12 = new RGBImage(color12);
        RGBImage image13 = new RGBImage(color13);
        image12.flipVertical();
        if ( image12.equals(image13) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #11");
        System.out.print("Testing invert color method - ");
        RGBColor[][] color14 = new RGBColor[5][5];
        RGBColor[][] color15 = new RGBColor[5][5];
        for (int i = 0; i < 5; i++){
            for (int j = 0; j < 5; j++){
                color14[i][j] = new RGBColor();
                color15[i][j] = new RGBColor(255,255,255);
            }
        }
        RGBImage image14 = new RGBImage(color14);
        RGBImage image15 = new RGBImage(color15);
        image14.invertColors();
        if ( image14.equals(image15) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #12");
        System.out.print("Testing rotate clockwise method - ");
        RGBColor[][] color16 = new RGBColor[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color16[i][j] = new RGBColor(i,i,i);
            }
        }
        RGBColor[][] color17 = new RGBColor[5][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color17[j][3-i] = new RGBColor(i,i,i);
            }
        }
        RGBImage image16 = new RGBImage(color16);
        RGBImage image17 = new RGBImage(color17);
        image16.rotateClockwise();
        if ( image16.equals(image16) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #13");
        System.out.print("Testing rotate counter-clockwise method - ");
        RGBColor[][] color18 = new RGBColor[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color18[i][j] = new RGBColor(i,i,i);
            }
        }
        RGBColor[][] color19 = new RGBColor[5][4];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color19[j][i] = new RGBColor(i,i,i);
            }
        }
        RGBImage image18 = new RGBImage(color18);
        RGBImage image19 = new RGBImage(color19);
        image18.rotateCounterClockwise();
        if ( image18.equals(image19) ){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #14");
        System.out.print("Testing shiftCol method - test 1/7 - ");
        RGBColor[][] color20 = new RGBColor[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color20[i][j] = new RGBColor(i+1,i+1,i+1);
            }
        }
        RGBColor[][] color20b = new RGBColor[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color20b[i][j] = new RGBColor();
            }
        }
        RGBImage image20b = new RGBImage(color20b);
        RGBImage image20 = new RGBImage(color20);
        RGBImage image21 = new RGBImage(image20);
        image21.shiftCol(1);
        int indicator14 = 0;
        for (int i = 0; i < 4; i++){
            if ( !(image21.toRGBColorArray()[i][0].equals(black)) || !(image21.toRGBColorArray()[i][1]).equals(image20.toRGBColorArray()[i][0]) ){
               indicator14 = 1;
               break;
            }
        }
        if (indicator14 == 0){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftCol method - test 2/7 - ");
        image21 = new RGBImage(image20);
        image21.shiftCol(5);
        if ( image21.equals(image20b) ){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftCol method - test 3/7 - ");
        image21 = new RGBImage(image20);
        image21.shiftCol(6);
        if ( image21.equals(image20) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftCol method - test 4/7 - ");
        image21.shiftCol(-6);
        if ( image21.equals(image20) ){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftCol method - test 5/7 - ");
        image21.shiftCol(-5);
        if ( image21.equals(image20b) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftCol method - test 6/7 - ");
        image21 = new RGBImage(image20);
        image21.shiftCol(-1);
        int indicator15 = 0;
        for (int i = 0; i < 4; i++){
            if ( !(image21.toRGBColorArray()[i][4].equals(black)) || !(image21.toRGBColorArray()[i][3]).equals(image20.toRGBColorArray()[i][4]) ){
               indicator15 = 1;
               break;
            }
        }
        if (indicator15 == 0){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftCol method - test 7/7 - ");
        image21 = new RGBImage(image20);
        image21.shiftCol(0);
        if ( image21.equals(image20) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #15");
        System.out.print("Testing shiftRows method - test 1/7 - ");
        RGBColor[][] color22 = new RGBColor[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color20[i][j] = new RGBColor(i+1,i+1,i+1);
            }
        }
        RGBColor[][] color22b = new RGBColor[4][5];
        for (int i = 0; i < 4; i++){
            for (int j = 0; j < 5; j++){
                color20b[i][j] = new RGBColor();
            }
        }
        RGBImage image22b = new RGBImage(color22b);
        RGBImage image22 = new RGBImage(color22);
        RGBImage image23 = new RGBImage(image22);
        image23.shiftRow(1);
        int indicator16 = 0;
        for (int i = 0; i < 5; i++){
            if ( !(image23.toRGBColorArray()[0][i].equals(black)) || !(image23.toRGBColorArray()[1][i]).equals(image22.toRGBColorArray()[0][i]) ){
               indicator16 = 1;
               break;
            }
        }
        if (indicator16 == 0){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        image23 = new RGBImage(image22);
        System.out.print("Testing shiftRows method - test 2/7 - ");
        image23.shiftRow(0);
        if ( image23.equals(image22) ){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftRows method - test 3/7 - ");
        image23.shiftRow(5);
        if ( image23.equals(image22) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftRows method - test 4/7 - ");
        image23.shiftRow(4);
        if ( image23.equals(image22b) ){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftRows method - test 5/7 - ");
        image23 = new RGBImage(image22);
        image23.shiftRow(-5);
        if ( image23.equals(image22) ){
            System.out.println(testPass); 
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        image23.shiftRow(-4);
        System.out.print("Testing shiftRows method - test 6/7 - ");
        if ( image23.equals(image22b) ){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        System.out.print("Testing shiftRows method - test 7/7 - ");
        image23 = new RGBImage(image22);
        image23.shiftRow(-1);
        indicator16 = 0;
        for (int i = 0; i < 5; i++){
            if ( !(image23.toRGBColorArray()[3][i].equals(black)) || !(image23.toRGBColorArray()[2][i]).equals(image22.toRGBColorArray()[3][i]) ){
               indicator16 = 1;
               break;
            }
        }
        if (indicator16 == 0){
            System.out.println(testPass);
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #16");
        System.out.print("Testing toGrayscale method - ");
        
        RGBColor[][] color25 = new RGBColor[5][5];
        RGBColor colorTest16 = new RGBColor(4,4,4);
        RGBColor colorTest17 = new RGBColor(3,3,3);
        RGBColor colorTest18 = new RGBColor(2,2,2);
        colorTest16.convertToGrayscale();
        colorTest17.convertToGrayscale();
        colorTest18.convertToGrayscale();
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++){
                color25[i][j] = new RGBColor(i,i,i);
            }
        }
        RGBImage image25 = new RGBImage(color25);
        double[][] grayTest = new double[5][5];
        grayTest = image25.toGrayscaleArray();
        if ( (grayTest[2][0] == colorTest18.convertToGrayscale()) && (grayTest[3][0] == colorTest17.convertToGrayscale()) && (grayTest[4][0] == colorTest16.convertToGrayscale()) ){
            System.out.println(testPass);    
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #17");
        System.out.println("Testing toString method - ");
        String test17 = ("(0,0,0) (0,0,0) (0,0,0) (0,0,0) (0,0,0)\n" +
        "(1,1,1) (1,1,1) (1,1,1) (1,1,1) (1,1,1)\n" +
        "(2,2,2) (2,2,2) (2,2,2) (2,2,2) (2,2,2)\n" +
        "(3,3,3) (3,3,3) (3,3,3) (3,3,3) (3,3,3)\n" +
        "(4,4,4) (4,4,4) (4,4,4) (4,4,4) (4,4,4)" ); 
        System.out.println(image25.toString());
        if (image25.toString().equals(test17)){
            System.out.println(testPass);   
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }
        
        System.out.println("");
        System.out.println("Test #18");
        System.out.print("Testing toRGBColorArray method - ");
        indicator16 = 0;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++){
                if ( !(color25[i][j].equals(image25.toRGBColorArray()[i][j])) ){
                    indicator16 = 1;
                }
            }
        }
        if (indicator16 == 0){
            System.out.println(testPass);   
        }
        else{
            System.out.println(testFail);
            testResult = false;
        }        
        
        System.out.println("");
        System.out.println("Test #19");
        System.out.println("Aliasing testing");
        System.out.println("Important notice!\n"+
        "This does not test all possible aliasing that you may have done.\n" +
        "Please be advised - check in your code for variables pointing to the same object in memory.");
        System.out.println("\u001B[32mTest started...\u001B[0m");
        RGBImage imageTest19a = new RGBImage(5,5);
        RGBImage imageTest19b = new RGBImage(5,5);
        RGBImage imageTest19c = new RGBImage(5,5);
        RGBColor colorTest19 = new RGBColor(222,222,222);
        //test 1
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in all-black-image constructor\u001B[0m"); 
            testResult = false;
        }
        
        imageTest19a = new RGBImage(color25);
        imageTest19b = new RGBImage(color25);
        //test 2
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in image from array constructor\u001B[0m");
            testResult = false;
        }

        imageTest19a = new RGBImage(imageTest19c);
        imageTest19b = new RGBImage(imageTest19c);
        //test 3
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in copy constructor\u001B[0m");            
            testResult = false;
        }

        imageTest19b.setPixel(0,0,colorTest19);
        imageTest19a.setPixel(0,0,colorTest19);
        //test4
        if (imageTest19a.getPixel(0,0) == imageTest19b.getPixel(0,0)) {
            System.out.println("\u001B[31mAliasing in setPixel method\u001B[0m");    
            testResult = false;
        }
        
        imageTest19a.flipHorizontal();
        imageTest19b.flipHorizontal();
        //test 5
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in flipHorizontal method\u001B[0m");   
            testResult = false;
        }
        
        imageTest19a.flipVertical();
        imageTest19b.flipVertical();
        //test 6
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in flipHorizontal method\u001B[0m");  
            testResult = false;
        }
        
        imageTest19a.invertColors();
        imageTest19b.invertColors();
        //test 7
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in flipHorizontal method\u001B[0m");       
            testResult = false;
        }
        
        imageTest19a.rotateClockwise();
        imageTest19b.rotateClockwise();
        //test 8
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in rotateClockwise method\u001B[0m");      
            testResult = false;
        }

        imageTest19a.rotateCounterClockwise();
        imageTest19b.rotateCounterClockwise();
        //test 9
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in rotateCounterClockwise method\u001B[0m");     
            testResult = false;
        }
  
        imageTest19a.shiftCol(2);
        imageTest19b.shiftCol(2);
        //test 10
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in shiftCol method\u001B[0m");   
            testResult = false;
        }

        imageTest19a.shiftRow(2);
        imageTest19b.shiftRow(2);
        //test 11
        if (imageTest19a == imageTest19b) {
            System.out.println("\u001B[31mAliasing in shiftRow method\u001B[0m");   
            testResult = false;
        }
        
        //test 12
        if (imageTest19a.toRGBColorArray() == imageTest19b.toRGBColorArray()) {
            System.out.println("\u001B[31mAliasing in toRGBColorArray method\u001B[0m");    
            testResult = false;
        }
        if (testResult){
            System.out.println("\u001B[32mAliasing test finished successfully.\u001B[0m");
        }
        
        
        if (testResult){
        System.out.println("\n\n");
        System.out.println("\u001B[32mTest all green. Great Success!\u001B[0m");
        }
        
      
        
        
        
        
        }
    }
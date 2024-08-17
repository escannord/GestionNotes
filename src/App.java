import classe.Etudiant;
import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
public class App {
    public static Scanner sc = new Scanner(System.in);
    public static double NOTE_ADMITTED=10;
    public static double NOTE_RATTRAPAGE=7;
    /**
     * The main entry point of the Java application.
     *
     * @param  args	an array of strings representing command-line arguments
     * @return     	none
     */
    public static void main(String[] args) throws Exception {
        System.out.print("Entrez le nombre d'etudiants: ");
        int n = sc.nextInt();
        Etudiant[] liste = insertData(n);
        Etudiant best = getBest(liste);
        Etudiant worst = getWorst(liste);
        double mean = getMean(liste);
        Etudiant[] admitted = getEtudiantsAdmitted(liste);
        Etudiant[] rattrapage = getEtudiantsRattrapage(liste);
        Etudiant[] recall = getEtudiantsRecaller(liste);
        
        /* 
         * Afficher les statistiques
         */
        System.out.println("");
        System.out.println("---------------------Liste des statistiques-----------------------------");
        System.out.println("Le pire etudiant est: "+worst.displayEtudiant()+" ");
        System.out.println("Le meilleur etudiant est: "+best.displayEtudiant()+" ");
        System.out.println("La moyenne est: "+mean);
        System.out.println("Les etudiants admis sont: ");
        printEtudiants(admitted);
        System.out.println("Les etudiants admis au rattrapage sont: ");
        printEtudiants(rattrapage);
        System.out.println("Les etudiants recaller sont: ");
        printEtudiants(recall);
        
        /* 
         * Sauvegarder les statistiques dans un fichier 
         */
        saveStatistics(best,worst,mean,admitted,rattrapage,recall);
    }

    /**
     * Inserts student data into an array of Etudiant objects.
     *
     * @param n	The number of students to insert data for
     * @return      An array of Etudiant objects containing the inserted student data
     */
    public static Etudiant[] insertData(int n){
        Etudiant[] liste=new Etudiant[n];
        for(int i=0;i<n;i++){
            System.out.print("Entrez le matricule["+(i+1)+"]: ");
            String matricule = sc.next();
            System.out.print("Entrez le nom["+(i+1)+"]: ");
            String nom = sc.next();
            System.out.print("Entrez le prenom["+(i+1)+"]: ");
            String prenom = sc.next();
            double moyenne=0;
            boolean isValid=false;
            do{
                try{
                    System.out.print("Entrez la moyenne(entre 0 et 20)["+(i+1)+"]:");
                    moyenne = sc.nextDouble();
                    isValid=true;
                }
                catch(Exception e){
                    System.out.println("Une erreur est survenue; veuillez recommencer.");
                    isValid=false;
                    sc.nextLine();
                }
                
            }
            while(!isValid || moyenne<0 || moyenne>20 );
            

            System.out.println("--------------------------------");
            liste[i] = new Etudiant(matricule,nom,prenom,moyenne);
        }
        sc.close(); // Fermez le scanner
        return liste;
    }

    /**
     * Returns the student with the highest average grade from the given list.
     *
     * @param  liste  the array of Etudiant objects to be evaluated
     * @return        the student with the highest average grade
     */
    public static Etudiant getBest(Etudiant[] liste){
        Etudiant best=liste[0];
        for(int i=1;i<liste.length;i++){
            if(liste[i].getMoyenne()>best.getMoyenne()){
                best=liste[i];
            }
        }
        return best;
    }

    /**
     * Returns the student with the lowest average grade from the given list.
     *
     * @param  liste  the array of Etudiant objects to be evaluated
     * @return        the student with the lowest average grade
     */
    public static Etudiant getWorst(Etudiant[] liste){
        Etudiant worst=liste[0];
        for(int i=1;i<liste.length;i++){
            if(liste[i].getMoyenne()<worst.getMoyenne()){
                worst=liste[i];
            }
        }
        return worst;
    }

    /**
     * Calculates the average grade of a list of students.
     *
     * @param  liste  the array of Etudiant objects to be evaluated
     * @return        the mean average grade of the students
     */
    public static double getMean(Etudiant[] liste){
        double sum=0;
        for(int i=0;i<liste.length;i++){
            sum+=liste[i].getMoyenne();
        }
        return sum/liste.length;
    }

    /**
     * Returns the number of students whose average grade is higher than the mean.
     *
     * @param  liste  the array of Etudiant objects to be evaluated
     * @return        the number of students with grades above the mean
     */
    public static int getNbEtudiantBestThanMean(Etudiant[] liste){
        int nb=0;
        double mean=getMean(liste);
        for(int i=0;i<liste.length;i++){
            if(liste[i].getMoyenne()>mean) {
                nb++;
            }
        }
        return nb;
    }

    /**
     * Returns an array of Etudiant objects containing only those students whose
     * average grade is greater than or equal to the admission threshold.
     *
     * @param  liste  the array of Etudiant objects to be filtered
     * @return        an array of Etudiant objects with grades meeting the admission threshold
     */
    public static Etudiant[] getEtudiantsAdmitted(Etudiant[] liste){
        Etudiant[] admitted = new Etudiant[liste.length];
        int j=0;
        for(int i=0;i<liste.length;i++){
            if(liste[i].getMoyenne() >= NOTE_ADMITTED){
                admitted[j]=liste[i];
                j++;
            }
        }
        return admitted;
    }

    /**
     * Returns an array of Etudiant objects containing only those students whose
     * average grade is within the rattrapage threshold, i.e., greater than or equal
     * to NOTE_RATTRAPAGE and less than NOTE_ADMITTED.
     *
     * @param  liste  the array of Etudiant objects to be filtered
     * @return        an array of Etudiant objects with grades within the rattrapage threshold
     */
    public static Etudiant[] getEtudiantsRattrapage(Etudiant[] liste){
        Etudiant[] rattrapage=new Etudiant[liste.length];
        int j=0;
        for(int i=0;i<liste.length;i++){
            if(liste[i].getMoyenne() >= NOTE_RATTRAPAGE && liste[i].getMoyenne() < NOTE_ADMITTED){
                rattrapage[j]=liste[i];
                j++;
            }
        }
        return rattrapage;
    }

    /**
     * Returns an array of Etudiant objects containing only those students whose
     * average grade is below the rattrapage threshold.
     *
     * @param  liste  the array of Etudiant objects to be filtered
     * @return         an array of Etudiant objects with grades below the rattrapage threshold
     */
    public static Etudiant[] getEtudiantsRecaller(Etudiant[] liste){
        Etudiant[] recall=new Etudiant[liste.length];
        int j=0;
        for(int i=0;i<liste.length;i++){
            if(liste[i].getMoyenne() < NOTE_RATTRAPAGE ){
                recall[j]=liste[i];
                j++;
            }
        }
        return recall;
    }


    /**
     * Prints the details of each student in the given list to the console.
     *
     * @param liste      the list of students to be printed
     */
    public static void printEtudiants(Etudiant[] liste){
        if (liste != null){
            for(int i=0;i<liste.length;i++){
                if(liste[i]!=null){
                    System.out.println(liste[i].displayEtudiant());
                }
            }
        }
        
    }

    /**
     * Writes the details of each student in the given list to the specified writer.
     *
     * @param liste      the list of students to be written
     * @param writer     the writer to which the student details are to be written
     * @throws IOException if an I/O error occurs while writing to the writer
     */
    public static void writeEtudiants(Etudiant[] liste, FileWriter writer) throws IOException{
        if (liste != null){
            for(int i=0;i<liste.length;i++){
                if(liste[i]!=null){
                    try {
                        writer.write(liste[i].displayEtudiant());
                        writer.write("\n");
                    }
                    catch (IOException err) {
                        System.err.println("Error: " + err.getMessage());
                    }
                    /* liste[i].displayEtudiant(); */
                }
            }
        }
    }

    /**
     * Saves student statistics to a file.
     *
     * @param best      the best student
     * @param worst     the worst student
     * @param mean      the mean score
     * @param admitted  the list of admitted students
     * @param rattrapage the list of students who need to retake
     * @param recall    the list of students who are recalled
     */
    public static void saveStatistics(Etudiant best, Etudiant worst, double mean, Etudiant[] admitted, Etudiant[] rattrapage, Etudiant[] recall){
        // Save statistics in a file 
        String nomFichier = "stat.txt"; // Spécifiez le nom du fichier

        try {
            // Créez un objet FileWriter
            FileWriter writer = new FileWriter(nomFichier);

            // Écrivez du contenu dans le fichier
            writer.write("---------------------Liste des statistiques-----------------------------\n");
            writer.write("Le pire etudiant est: "+worst.displayEtudiant()+" \n");
            writer.write("Le meilleur etudiant est: "+best.displayEtudiant()+" \n");
            writer.write("La moyenne est: "+mean+"\n");
            writer.write("Les etudiants admis sont: \n");
            writeEtudiants(admitted,writer);
            writer.write("Les etudiants admis au rattrapage sont: \n");
            writeEtudiants(rattrapage,writer);
            writer.write("Les etudiants recallés sont: \n");
            writeEtudiants(recall,writer);
            // Fermez le writer
            writer.close();

            System.out.println("Statistiques sauvegardées.");
        } catch (IOException e) {
            System.err.println("Erreur lors de l'écriture dans le fichier : " + e.getMessage());
        }
        
    }
}

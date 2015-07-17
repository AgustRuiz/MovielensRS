package es.AgustRuiz.RecommenderSystem;

/**
 * Main class
 *
 * @author Agustin Ruiz Linares <arl00029@red.ujaen.es>
 */
public class Main {

    public static UserHandler usersHandler;
    public static ItemHandler itemsHandler;
    public static GenericRatingHandler ratingsHandler;
    public static GenericRatingHandler ratingsTrainingHandler;
    public static GenericRatingHandler ratingsTestHandler;
    public static NeighborsHandler neighborhoodHandler;
    public static NeighborsHandler trainingNeighborhoodHandler;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        if (!DbConnection.isOk()) {
            System.err.println("Can't connect to database!");
        } else {
            long time;

            // USERS
            System.out.print("Loading users... ");
            time = System.currentTimeMillis();
            usersHandler = new UserHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // ITEMS
            System.out.print("Loading items... ");
            time = System.currentTimeMillis();
            itemsHandler = new ItemHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // RATINGS 
            System.out.print("Loading ratings and building rating matrix... ");
            time = System.currentTimeMillis();
            ratingsHandler = new RatingHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // TRAINING RATINGS 
            System.out.print("Loading training ratings and building training rating matrix... ");
            time = System.currentTimeMillis();
            ratingsTrainingHandler = new RatingTrainingHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // TEST RATINGS 
            System.out.print("Loading test ratings and building training rating matrix... ");
            time = System.currentTimeMillis();
            ratingsTestHandler = new RatingTestHandler();
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // NEIGHBORS HANDLER
            System.out.print("Building neighbors handler... ");
            time = System.currentTimeMillis();
            neighborhoodHandler = new NeighborsHandler(itemsHandler, usersHandler, ratingsHandler);
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            // NEIGHBORS TRAINING HANDLER
            System.out.print("Building training neighbors handler... ");
            time = System.currentTimeMillis();
            trainingNeighborhoodHandler = new NeighborsHandler(itemsHandler, usersHandler, ratingsTrainingHandler);
            System.out.println("Done in " + (System.currentTimeMillis() - time) + "ms!");

            GUI.MainMenu();
        }
    }

}

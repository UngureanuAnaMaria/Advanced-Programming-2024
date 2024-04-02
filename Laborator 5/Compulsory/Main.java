public class Main {
    public static void main(String[] args) {
        Main app = new Main();
        app.testRepo();
        //app.testLoadView();
    }

    private void testRepo() {
        var repo = new Repository("C:\\Users\\anaun\\OneDrive\\Desktop\\Lab5");
        // var doc = repo.findDocument("...");
        repo.loadDocuments();
        repo.printMap();
    }

}

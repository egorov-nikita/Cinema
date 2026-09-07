public class Movie {
    private String name;
    private int age;
    private int raiting;
    private String description;
    private String posterPath;
    private String videoPath;
    private String director;

    public Movie(String name, int age, int raiting, String description, String posterPath, String videoPath, String director) {
        this.name = name;
        this.age = age;
        this.raiting = raiting;
        this.description = description;
        this.posterPath = posterPath;
        this.videoPath = videoPath;
        this.director = director;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public int getRaiting() {
        return raiting;
    }

    public String getDescription() {
        return description;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getVideoPath() {
        return videoPath;
    }

    public String getDirector() {
        return director;
    }

    @Override
    public String toString() {
        return
                 name + "("+ age +") " ;
    }


}

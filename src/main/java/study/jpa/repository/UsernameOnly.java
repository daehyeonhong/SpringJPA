package study.jpa.repository;

public interface UsernameOnly {
    //    @Value(value = "#{target.username+' '+target.age}") OpenProjections
    String getUsername();//CloseProjections
}

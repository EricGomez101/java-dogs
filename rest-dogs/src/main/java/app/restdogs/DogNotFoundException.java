package app.restdogs;

public class DogNotFoundException extends RuntimeException
{
    public <E> DogNotFoundException(E id)
    {
        super("Could not find employee " + id);
    }
}

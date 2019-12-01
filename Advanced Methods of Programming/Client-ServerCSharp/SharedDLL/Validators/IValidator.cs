namespace SharedDLL.Validators
{
    public interface IValidator<E>
    {
        void Validate(E entity);
    }
}
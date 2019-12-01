namespace Lab1C_MPP.Validators
{
    interface IValidator<E>
    {
        void Validate(E entity);
    }
}
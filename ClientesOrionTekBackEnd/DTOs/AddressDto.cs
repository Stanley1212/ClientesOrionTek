namespace ClientesOrionTekBackEnd.DTOs;

public class AddressDto
{
    public long AddressId { get; set; }
    public long ClientId { get; set; }
    public string Street { get; set; } = string.Empty;
    public string City { get; set; } = string.Empty;
    public string Sector { get; set; } = string.Empty;
    public string HouseNumber { get; set; } = string.Empty;
}

public class CreateAddressDto
{
    public long ClientId { get; set; }
    public string Street { get; set; } = string.Empty;
    public string City { get; set; } = string.Empty;
    public string Sector { get; set; } = string.Empty;
    public string HouseNumber { get; set; } = string.Empty;
}

public class UpdateAddressDto
{
    public string Street { get; set; } = string.Empty;
    public string City { get; set; } = string.Empty;
    public string Sector { get; set; } = string.Empty;
    public string HouseNumber { get; set; } = string.Empty;
}

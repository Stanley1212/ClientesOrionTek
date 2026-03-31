using System.ComponentModel.DataAnnotations;
using System.ComponentModel.DataAnnotations.Schema;

namespace ClientesOrionTekBackEnd.Models;

public class Address
{
    [Key]
    [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
    public long AddressId { get; set; }

    [Required]
    public long ClientId { get; set; }

    [Required]
    [MaxLength(255)]
    public string Street { get; set; } = string.Empty;

    [Required]
    [MaxLength(100)]
    public string City { get; set; } = string.Empty;

    [Required]
    [MaxLength(100)]
    public string Sector { get; set; } = string.Empty;

    [Required]
    [MaxLength(20)]
    public string HouseNumber { get; set; } = string.Empty;

    [ForeignKey(nameof(ClientId))]
    public virtual Client? Client { get; set; }
}

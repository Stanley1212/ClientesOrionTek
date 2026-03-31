using Microsoft.EntityFrameworkCore;
using ClientesOrionTekBackEnd.Models;

namespace ClientesOrionTekBackEnd.Data;

public class AppDbContext : DbContext
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
    }

    public DbSet<Client> Clients { get; set; }
    public DbSet<Address> Addresses { get; set; }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<Client>(entity =>
        {
            entity.HasKey(e => e.Id);
            entity.Property(e => e.FirstName).IsRequired().HasMaxLength(100);
            entity.Property(e => e.LastName).IsRequired().HasMaxLength(100);
            entity.Property(e => e.Phone).IsRequired().HasMaxLength(20);
            entity.Property(e => e.Email).IsRequired().HasMaxLength(255);
        });

        modelBuilder.Entity<Address>(entity =>
        {
            entity.HasKey(e => e.AddressId);
            entity.Property(e => e.Street).IsRequired().HasMaxLength(255);
            entity.Property(e => e.City).IsRequired().HasMaxLength(100);
            entity.Property(e => e.Sector).IsRequired().HasMaxLength(100);
            entity.Property(e => e.HouseNumber).IsRequired().HasMaxLength(20);

            entity.HasOne(e => e.Client)
                .WithMany(c => c.Addresses)
                .HasForeignKey(e => e.ClientId)
                .OnDelete(DeleteBehavior.Cascade);
        });
    }
}

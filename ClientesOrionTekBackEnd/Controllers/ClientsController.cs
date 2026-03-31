using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ClientesOrionTekBackEnd.Data;
using ClientesOrionTekBackEnd.DTOs;
using ClientesOrionTekBackEnd.Models;

namespace ClientesOrionTekBackEnd.Controllers;

[ApiController]
[Route("api/[controller]")]
public class ClientsController : ControllerBase
{
    private readonly AppDbContext _context;

    public ClientsController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<ClientDto>>> GetClients()
    {
        var clients = await _context.Clients
            .Include(c => c.Addresses)
            .ToListAsync();

        return Ok(clients.Select(MapToDto));
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<ClientDto>> GetClient(long id)
    {
        var client = await _context.Clients
            .Include(c => c.Addresses)
            .FirstOrDefaultAsync(c => c.Id == id);

        if (client == null)
            return NotFound();

        return Ok(MapToDto(client));
    }

    [HttpPost]
    public async Task<ActionResult<ClientDto>> CreateClient(CreateClientDto createDto)
    {
        var client = new Client
        {
            FirstName = createDto.FirstName,
            LastName = createDto.LastName,
            Phone = createDto.Phone,
            Email = createDto.Email
        };

        _context.Clients.Add(client);
        await _context.SaveChangesAsync();

        return CreatedAtAction(nameof(GetClient), new { id = client.Id }, MapToDto(client));
    }

    [HttpPut("{id}")]
    public async Task<ActionResult<ClientDto>> UpdateClient(long id, UpdateClientDto updateDto)
    {
        var client = await _context.Clients.FindAsync(id);
        if (client == null)
            return NotFound();

        client.FirstName = updateDto.FirstName;
        client.LastName = updateDto.LastName;
        client.Phone = updateDto.Phone;
        client.Email = updateDto.Email;

        await _context.SaveChangesAsync();

        var updatedClient = await _context.Clients
            .Include(c => c.Addresses)
            .FirstOrDefaultAsync(c => c.Id == id);

        return Ok(MapToDto(updatedClient!));
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteClient(long id)
    {
        var client = await _context.Clients.FindAsync(id);
        if (client == null)
            return NotFound();

        _context.Clients.Remove(client);
        await _context.SaveChangesAsync();

        return NoContent();
    }

    private static ClientDto MapToDto(Client client)
    {
        return new ClientDto
        {
            Id = client.Id,
            FirstName = client.FirstName,
            LastName = client.LastName,
            Phone = client.Phone,
            Email = client.Email,
            Addresses = client.Addresses?.Select(a => new AddressDto
            {
                AddressId = a.AddressId,
                ClientId = a.ClientId,
                Street = a.Street,
                City = a.City,
                Sector = a.Sector,
                HouseNumber = a.HouseNumber
            }).ToList() ?? new List<AddressDto>()
        };
    }
}

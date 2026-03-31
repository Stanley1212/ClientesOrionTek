using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using ClientesOrionTekBackEnd.Data;
using ClientesOrionTekBackEnd.DTOs;
using ClientesOrionTekBackEnd.Models;

namespace ClientesOrionTekBackEnd.Controllers;

[ApiController]
[Route("api/[controller]")]
public class AddressesController : ControllerBase
{
    private readonly AppDbContext _context;

    public AddressesController(AppDbContext context)
    {
        _context = context;
    }

    [HttpGet]
    public async Task<ActionResult<IEnumerable<AddressDto>>> GetAddresses()
    {
        var addresses = await _context.Addresses.ToListAsync();
        return Ok(addresses.Select(MapToDto));
    }

    [HttpGet("{id}")]
    public async Task<ActionResult<AddressDto>> GetAddress(long id)
    {
        var address = await _context.Addresses.FindAsync(id);
        if (address == null)
            return NotFound();

        return Ok(MapToDto(address));
    }

    [HttpGet("client/{clientId}")]
    public async Task<ActionResult<IEnumerable<AddressDto>>> GetAddressesByClient(long clientId)
    {
        var addresses = await _context.Addresses
            .Where(a => a.ClientId == clientId)
            .ToListAsync();

        return Ok(addresses.Select(MapToDto));
    }

    [HttpPost]
    public async Task<ActionResult<AddressDto>> CreateAddress(CreateAddressDto createDto)
    {
        var clientExists = await _context.Clients.AnyAsync(c => c.Id == createDto.ClientId);
        if (!clientExists)
            return BadRequest("Client does not exist");

        var address = new Address
        {
            ClientId = createDto.ClientId,
            Street = createDto.Street,
            City = createDto.City,
            Sector = createDto.Sector,
            HouseNumber = createDto.HouseNumber
        };

        _context.Addresses.Add(address);
        await _context.SaveChangesAsync();

        return CreatedAtAction(nameof(GetAddress), new { id = address.AddressId }, MapToDto(address));
    }

    [HttpPut("{id}")]
    public async Task<ActionResult<AddressDto>> UpdateAddress(long id, UpdateAddressDto updateDto)
    {
        var address = await _context.Addresses.FindAsync(id);
        if (address == null)
            return NotFound();

        address.Street = updateDto.Street;
        address.City = updateDto.City;
        address.Sector = updateDto.Sector;
        address.HouseNumber = updateDto.HouseNumber;

        await _context.SaveChangesAsync();

        return Ok(MapToDto(address));
    }

    [HttpDelete("{id}")]
    public async Task<IActionResult> DeleteAddress(long id)
    {
        var address = await _context.Addresses.FindAsync(id);
        if (address == null)
            return NotFound();

        _context.Addresses.Remove(address);
        await _context.SaveChangesAsync();

        return NoContent();
    }

    private static AddressDto MapToDto(Address address)
    {
        return new AddressDto
        {
            AddressId = address.AddressId,
            ClientId = address.ClientId,
            Street = address.Street,
            City = address.City,
            Sector = address.Sector,
            HouseNumber = address.HouseNumber
        };
    }
}

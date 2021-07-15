@RestController
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @RequestMapping(method = RequestMethod.POST)
    Product create(@RequestBody Product product){
        return productRepository.save(product);
    }
    @RequestMapping(value="/{id", method = RequestMethod.GET)
    Optional<Product> findById(@PathVariable Integer id){
        return productRepository.findById(id);
    }
}
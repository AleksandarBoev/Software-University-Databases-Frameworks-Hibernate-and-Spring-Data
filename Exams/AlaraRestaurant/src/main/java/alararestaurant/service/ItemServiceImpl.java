package alararestaurant.service;

import alararestaurant.constants.ImportConstants;
import alararestaurant.domain.dtos.import_dtos.items.ItemImportDto;
import alararestaurant.domain.entities.Category;
import alararestaurant.domain.entities.Item;
import alararestaurant.repository.CategoryRepository;
import alararestaurant.repository.ItemRepository;
import alararestaurant.util.FileUtil;
import alararestaurant.util.ValidationUtil;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class ItemServiceImpl implements ItemService {
    private ItemRepository itemRepository;
    private FileUtil fileUtil;
    private Gson gson;
    private ValidationUtil validationUtil;
    private CategoryRepository categoryRepository;

    @Autowired
    public ItemServiceImpl(ItemRepository itemRepository, FileUtil fileUtil, Gson gson, ValidationUtil validationUtil, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Boolean itemsAreImported() {
        return this.itemRepository.count() > 0;
    }

    @Override
    public String readItemsJsonFile() throws IOException {
        String relativePath = "files/items.json";
        return this.fileUtil.readFile(relativePath);
    }

    @Override
    public String importItems(String items) {
        //•	If any validation errors occur (such as invalid item name
        // or invalid category name), ignore the entity and print an error message.
        //•	If an item with the same name already exists, ignore the
        // entity and do not import it.
        //•	If an item’s category doesn’t exist, create it along with the item.

        String itemsJson = items;
        ItemImportDto[] itemImportDtos = this.gson.fromJson(itemsJson, ItemImportDto[].class);

        StringBuilder resultFromImports = new StringBuilder();

        for (ItemImportDto itemImportDto : itemImportDtos) {
            if (!this.validationUtil.isValid(itemImportDto)) {
                resultFromImports.append(ImportConstants.INVALID_DATA).append(System.lineSeparator());
                continue;
            }

            String itemName = itemImportDto.getName();
            if (this.itemRepository.findItemByName(itemName) != null) { //duplicate data
                resultFromImports.append(ImportConstants.INVALID_DATA).append(System.lineSeparator());
                continue;
            }

            String categoryName = itemImportDto.getCategory();
            Category category = this.categoryRepository.findCategoryByName(categoryName);

            if (category == null) {
                category = new Category();
                category.setName(categoryName);
                this.categoryRepository.saveAndFlush(category);
            }

            Item item = new Item();
            item.setCategory(category);
            item.setName(itemName);
            item.setPrice(itemImportDto.getPrice());

            this.itemRepository.saveAndFlush(item);

            resultFromImports.append(String.format(ImportConstants.SUCCESSFUL_IMPORT, itemName))
                    .append(System.lineSeparator());
        }

        return resultFromImports.toString().trim();
    }
}

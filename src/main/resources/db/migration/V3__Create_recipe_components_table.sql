-- Каждый компонент ссылается либо на ингредиент, либо на другой рецепт.
CREATE TABLE recipe_components (
  id SERIAL PRIMARY KEY,
  recipe_id INT NOT NULL,
  ingredient_id INT,
  sub_recipe_id INT,
  quantity NUMERIC NOT NULL,
  CONSTRAINT fk_recipe FOREIGN KEY (recipe_id) REFERENCES recipes(id),
  CONSTRAINT fk_ingredient FOREIGN KEY (ingredient_id) REFERENCES ingredients(id),
  CONSTRAINT fk_sub_recipe FOREIGN KEY (sub_recipe_id) REFERENCES recipes(id),
  CONSTRAINT chk_component CHECK (
    (ingredient_id IS NOT NULL AND sub_recipe_id IS NULL)
     OR
    (ingredient_id IS NULL AND sub_recipe_id IS NOT NULL)
  )
);

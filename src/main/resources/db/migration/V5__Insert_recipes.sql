INSERT INTO ingredients (name, quantity) VALUES ('Вода', 10000), ('Кофе', 5000), ('Молоко', 5000);

-- Эспрессо
INSERT INTO recipes (name) VALUES ('Эспрессо');
INSERT INTO recipe_components (recipe_id, ingredient_id, quantity) VALUES
  ((SELECT id FROM recipes WHERE name = 'Эспрессо'), (SELECT id FROM ingredients WHERE name = 'Вода'), 100),
  ((SELECT id FROM recipes WHERE name = 'Эспрессо'), (SELECT id FROM ingredients WHERE name = 'Кофе'), 20);

-- Американо
INSERT INTO recipes (name) VALUES ('Американо');
INSERT INTO recipe_components (recipe_id, sub_recipe_id, quantity) VALUES
  ((SELECT id FROM recipes WHERE name = 'Американо'), (SELECT id FROM recipes WHERE name = 'Эспрессо'), 1);
INSERT INTO recipe_components (recipe_id, ingredient_id, quantity) VALUES
  ((SELECT id FROM recipes WHERE name = 'Американо'), (SELECT id FROM ingredients WHERE name = 'Вода'), 100);

-- Капучино
INSERT INTO recipes (name) VALUES ('Капучино');
INSERT INTO recipe_components (recipe_id, sub_recipe_id, quantity) VALUES
  ((SELECT id FROM recipes WHERE name = 'Капучино'), (SELECT id FROM recipes WHERE name = 'Эспрессо'), 2);
INSERT INTO recipe_components (recipe_id, ingredient_id, quantity) VALUES
  ((SELECT id FROM recipes WHERE name = 'Капучино'), (SELECT id FROM ingredients WHERE name = 'Молоко'), 50);
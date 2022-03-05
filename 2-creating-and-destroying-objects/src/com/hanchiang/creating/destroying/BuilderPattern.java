package com.hanchiang.creating.destroying;

/**
 * Static factories and constructors share a limitation: they do not scale well to large numbers of
 * optional parameters.
 * Instead of making the desired object directly, the client calls a
 * constructor (or static factory) with all the required parameters and gets a builder object.
 * Then the client calls setter-like methods on the builder object to set each optional parameter of
 * interest.
 * Finally, the client calls a parameterless build method to generate the object, which is
 * typically immutable.
 * + Able to enforce validity of state in the Builder class.
 * + Can have variable number of parameters
 * + Readable
 * - In performance-critical situations, builder pattern isn't suitable due to the additional cost of builder creation
 */
public class BuilderPattern {
  private final int servingSize;
  private final int servings;
  private final int calories;
  private final int fat;
  private final int sodium;
  private final int carbohydrate;

  public static class Builder {
    // Required parameters
    private final int servingSize;
    private final int servings;

    // Optional parameters - initialized to default values
    private int calories = 0;
    private int fat = 0;
    private int sodium = 0;
    private int carbohydrate = 0;

    public Builder(int servingSize, int servings) {
      this.servingSize = servingSize;
      this.servings = servings;
    }

    public Builder calories(int val) {
      calories = val;
      return this;
    }

    public Builder fat(int val) {
      fat = val;
      return this;
    }

    public Builder sodium(int val) {
      sodium = val;
      return this;
    }

    public Builder carbohydrate(int val) {
      carbohydrate = val;
      return this;
    }

    public BuilderPattern build() {
      return new BuilderPattern(this);
    }
  }

  private BuilderPattern(Builder builder) {
    servingSize = builder.servingSize;
    servings = builder.servings;
    calories = builder.calories;
    fat = builder.fat;
    sodium = builder.sodium;
    carbohydrate = builder.carbohydrate;
  }

  public static void main(String[] args) {
    BuilderPattern cocaCola = new BuilderPattern.Builder(240, 8).calories(100).sodium(100).carbohydrate(27).build();

    System.out.println(cocaCola);
  }
}

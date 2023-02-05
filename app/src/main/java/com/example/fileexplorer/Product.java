package com.example.fileexplorer;

import java.util.Comparator;

public class Product {

    private Boolean isDirectory;
    private String name;
    private String path;
    private Boolean checked;

    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    public Boolean getChecked() {
        return checked;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Product(Boolean isDirectory, String name, String path, Boolean checked) {
        this.isDirectory = isDirectory;
        this.name = name;
        this.path = path;
        this.checked = checked;
    }

    public Boolean getDirectory() {
        return isDirectory;
    }

    public void setDirectory(Boolean directory) {
        isDirectory = directory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static Comparator<Product> ProductNameComparator = new Comparator<Product>() {

        public int compare(Product s1, Product s2) {
            String ProductName1 = s1.getName().toUpperCase();
            String ProductName2 = s2.getName().toUpperCase();

            //ascending order
            return ProductName1.compareTo(ProductName2);

            //descending order
            //return ProductName2.compareTo(ProductName1);
        }};

    @Override
    public String toString() {
        return "[ isDirectory=" + isDirectory + ", name=" + name + "]";
    }
}


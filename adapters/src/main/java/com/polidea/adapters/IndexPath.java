package com.polidea.adapters;

public class IndexPath {

    public static final IndexPath INVALID_PATH = new IndexPath(-1, -1);

    public static final int SECTION_HEADER = -1;

    protected int section;

    protected int row;

    public IndexPath(int section, int row) {
        this.section = section;
        this.row = row;
    }

    public boolean isSection() {
        return row == SECTION_HEADER;
    }

    public int getSection() {
        return section;
    }

    public int getRow() {
        return row;
    }

    @Override
    public String toString() {
        return "[" + "section=" + section + ", row=" + row + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        IndexPath indexPath = (IndexPath) o;
        return section == indexPath.section && row == indexPath.row;
    }

    @Override
    public int hashCode() {
        int result = section;
        result = 31 * result + row;
        return result;
    }
}

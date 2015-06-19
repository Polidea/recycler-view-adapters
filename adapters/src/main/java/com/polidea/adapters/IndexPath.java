package com.polidea.adapters;

public class IndexPath {

    public static final IndexPath INVALID_PATH = new IndexPath(-1, -1);

    public static final int SECTION_HEADER = -1;

    public int section;

    public int row;

    public IndexPath(int section, int row) {
        this.section = section;
        this.row = row;
    }

    public boolean isSection() {
        return row == SECTION_HEADER;
    }

    public boolean equals(IndexPath indexPath) {
        return indexPath.row == row && indexPath.section == section;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof IndexPath) {
            IndexPath indexPath = (IndexPath) o;
            return equals(indexPath);
        }
        return super.equals(o);
    }

    @Override
    public String toString() {
        return "[" + "section=" + section + ", row=" + row + "]";
    }
}

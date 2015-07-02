package com.polidea.adapters

import spock.lang.Specification

class BaseSectionSpanSizeLookupTest extends Specification {

    def adapterMock = Mock(BaseSectionRecyclerViewAdapter)

    def fullSpanSize = 2

    def defaultDataSpanSize = 1

    def coughtIndexPath

    def spanSizeLookup = new BaseSectionSpanSizeLookup(adapterMock, fullSpanSize) {

        @Override
        protected int getRowSpanSize(IndexPath indexPath) {
            coughtIndexPath = indexPath
            return defaultDataSpanSize
        }
    }

    def "get data span size should return full span size when is section header"() {
        setup:
        adapterMock.getIndexPathForDataPosition(10) >> new IndexPath(0, IndexPath.SECTION_HEADER)

        expect:
        spanSizeLookup.getDataSpanSize(10) == fullSpanSize
    }

    def "get data span size should return default span size when is not section header"() {
        setup:
        adapterMock.getIndexPathForDataPosition(10) >> new IndexPath(0, 0)

        expect:
        spanSizeLookup.getDataSpanSize(10) == defaultDataSpanSize
        coughtIndexPath == new IndexPath(0, 0)
    }
}

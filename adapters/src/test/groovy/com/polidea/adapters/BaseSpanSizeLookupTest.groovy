package com.polidea.adapters

import spock.lang.Specification

class BaseSpanSizeLookupTest extends Specification {

    def adapterMock = Mock(BaseRecyclerViewAdapter)

    def fullSpanSize = 2

    def coughtDataPosition

    def defaultDataSpanSize = 1

    def spanSizeLookup = new BaseSpanSizeLookup(adapterMock, fullSpanSize) {

        @Override
        protected int getDataSpanSize(int dataPosition) {
            coughtDataPosition = dataPosition
            return defaultDataSpanSize
        }
    }

    def "get span size should return full span size when is full span item position"() {
        setup:
        adapterMock.isFullSpanItemPosition(10) >> true

        expect:
        spanSizeLookup.getSpanSize(10) == fullSpanSize
    }

    def "get span size should return default span size when is not full span item position"() {
        setup:
        adapterMock.isFullSpanItemPosition(10) >> false
        adapterMock.getDataPosition(10) >> 11

        expect:
        spanSizeLookup.getSpanSize(10) == defaultDataSpanSize
        coughtDataPosition == 11
    }
}

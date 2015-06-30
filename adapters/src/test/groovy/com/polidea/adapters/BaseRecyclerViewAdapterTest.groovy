package com.polidea.adapters

import spock.lang.Specification
import spock.lang.Unroll

class BaseRecyclerViewAdapterTest extends Specification {

    static INFINITE_SCROLLING_VIEW_TYPE = 100

    static TOP_CONTENT_INSET_VIEW_TYPE = 101

    static BOTTOM_CONTENT_INSET_VIEW_TYPE = 102

    static DATA_VIEW_TYPE = 103

    def adapter = new RecyclerViewAdapter()

    @Unroll
    def "get data position for adapterPosition = #adapterPosition and topContentInset = #topContentInset should equal #dataPosition"() {
        setup:
        adapter.dataCount = 2
        adapter.topContentInset = topContentInset

        expect:
        adapter.getDataPosition(adapterPosition) == dataPosition

        where:
        adapterPosition | topContentInset | dataPosition
        0               | 10              | -1
        1               | 10              | 0
        2               | 10              | 1
        0               | C.NO_VALUE      | 0
        1               | C.NO_VALUE      | 1
    }

    @Unroll
    def "get adapter position for [dataPosition = #dataPosition, topContentInset = #topContentInset] should equal #adapterPosition"() {
        setup:
        adapter.dataCount = 2
        adapter.topContentInset = topContentInset

        expect:
        adapter.getAdapterPosition(dataPosition) == adapterPosition

        where:
        dataPosition | topContentInset | adapterPosition
        -1           | 10              | 0
        0            | 10              | 1
        1            | 10              | 2
        0            | C.NO_VALUE      | 0
        1            | C.NO_VALUE      | 1
    }

    @Unroll
    def "get item view type for [position = #position, infiniteScrollingEnabled = #infiniteScrollingEnabled, topContentInset = #topContentInset, bottomContentInset = #bottomContentInset] should equal #itemViewType"() {
        given:
        adapter.dataCount = 2
        adapter.infiniteScrollingEnabled = infiniteScrollingEnabled
        adapter.infiniteScrollingLayoutResId = INFINITE_SCROLLING_VIEW_TYPE
        adapter.topContentInset = topContentInset
        adapter.topContentInsetViewLayoutResId = TOP_CONTENT_INSET_VIEW_TYPE
        adapter.bottomContentInsetViewLayoutResId = BOTTOM_CONTENT_INSET_VIEW_TYPE
        adapter.bottomContentInset = bottomContentInset
        adapter.dataViewLayoutResId = DATA_VIEW_TYPE

        expect:
        adapter.getItemViewType(position) == itemViewType

        where:
        position | infiniteScrollingEnabled | topContentInset | bottomContentInset | itemViewType
        0        | 0                        | 10              | C.NO_VALUE         | TOP_CONTENT_INSET_VIEW_TYPE
        0        | 0                        | C.NO_VALUE      | C.NO_VALUE         | DATA_VIEW_TYPE
        1        | 0                        | C.NO_VALUE      | C.NO_VALUE         | DATA_VIEW_TYPE
        2        | 0                        | C.NO_VALUE      | 10                 | BOTTOM_CONTENT_INSET_VIEW_TYPE
        2        | 1                        | C.NO_VALUE      | C.NO_VALUE         | INFINITE_SCROLLING_VIEW_TYPE
        2        | 0                        | 10              | C.NO_VALUE         | DATA_VIEW_TYPE
        3        | 0                        | 10              | 10                 | BOTTOM_CONTENT_INSET_VIEW_TYPE
        3        | 1                        | C.NO_VALUE      | 10                 | BOTTOM_CONTENT_INSET_VIEW_TYPE
        3        | 1                        | 10              | 10                 | INFINITE_SCROLLING_VIEW_TYPE
        4        | 1                        | 10              | 10                 | BOTTOM_CONTENT_INSET_VIEW_TYPE
    }

    @Unroll
    def "get item id for [position = #position, infiniteScrollingEnabled = #infiniteScrollingEnabled, topContentInset = #topContentInset, bottomContentInset = #bottomContentInset] should equal #id"() {
        given:
        adapter.dataCount = 2
        adapter.infiniteScrollingEnabled = infiniteScrollingEnabled
        adapter.topContentInset = topContentInset
        adapter.bottomContentInset = bottomContentInset
        adapter.infiniteScrollingLayoutResId = INFINITE_SCROLLING_VIEW_TYPE
        adapter.topContentInsetViewLayoutResId = TOP_CONTENT_INSET_VIEW_TYPE
        adapter.bottomContentInsetViewLayoutResId = BOTTOM_CONTENT_INSET_VIEW_TYPE
        adapter.dataViewLayoutResId = DATA_VIEW_TYPE

        and:
        adapter.dataItemViewIdMap.put(0, 0)
        adapter.dataItemViewIdMap.put(1, 1)

        expect:
        adapter.getItemId(position) == id

        where:
        position | infiniteScrollingEnabled | topContentInset | bottomContentInset | id
        0        | 0                        | 10              | C.NO_VALUE         | C.TOP_CONTENT_INSET_ROW_ID
        0        | 0                        | C.NO_VALUE      | C.NO_VALUE         | 0
        1        | 0                        | C.NO_VALUE      | C.NO_VALUE         | 1
        2        | 0                        | C.NO_VALUE      | 10                 | C.BOTTOM_CONTENT_INSET_ROW_ID
        2        | 1                        | C.NO_VALUE      | C.NO_VALUE         | C.INFINITE_SCROLLING_ROW_ID
        2        | 0                        | 10              | C.NO_VALUE         | 1
        3        | 0                        | 10              | 10                 | C.BOTTOM_CONTENT_INSET_ROW_ID
        3        | 1                        | C.NO_VALUE      | 10                 | C.BOTTOM_CONTENT_INSET_ROW_ID
        3        | 1                        | 10              | 10                 | C.INFINITE_SCROLLING_ROW_ID
        4        | 1                        | 10              | 10                 | C.BOTTOM_CONTENT_INSET_ROW_ID
    }

    @Unroll
    def "get item count for [dataCount = #dataCount, infiniteScrollingEnabled = #infiniteScrollingEnabled, topContentInset = #topContentInset, bottomContentInset = #bottomContentInset] should equal #itemCount"() {
        setup:
        adapter.dataCount = dataCount
        adapter.infiniteScrollingEnabled = infiniteScrollingEnabled
        adapter.topContentInset = topContentInset
        adapter.bottomContentInset = bottomContentInset

        expect:
        adapter.getItemCount() == itemCount

        where:
        dataCount | infiniteScrollingEnabled | topContentInset | bottomContentInset || itemCount
        0         | 0                        | C.NO_VALUE      | C.NO_VALUE         || 0
        0         | 1                        | C.NO_VALUE      | C.NO_VALUE         || 1
        0         | 0                        | 1               | C.NO_VALUE         || 1
        0         | 0                        | C.NO_VALUE      | 1                  || 1
        0         | 1                        | C.NO_VALUE      | 1                  || 2
        0         | 0                        | 1               | 1                  || 2
        0         | 1                        | 1               | C.NO_VALUE         || 2
        0         | 1                        | 1               | 1                  || 3
        1         | 0                        | C.NO_VALUE      | C.NO_VALUE         || 1
        1         | 1                        | C.NO_VALUE      | C.NO_VALUE         || 2
        1         | 0                        | 1               | C.NO_VALUE         || 2
        1         | 0                        | C.NO_VALUE      | 1                  || 2
        1         | 1                        | C.NO_VALUE      | 1                  || 3
        1         | 0                        | 1               | 1                  || 3
        1         | 1                        | 1               | C.NO_VALUE         || 3
        1         | 1                        | 1               | 1                  || 4

    }

    @Unroll
    def "is infinite scrolling position for [position = #position, infiniteScrollingEnabled = #infiniteScrollingEnabled, bottomContentInset = #bottomContentInset] should equal #isInfiniteScrollingPosition"() {
        setup:
        adapter.dataCount = dataCount
        adapter.infiniteScrollingEnabled = infiniteScrollingEnabled
        adapter.bottomContentInset = bottomContentInset

        expect:
        adapter.isInfiniteScrollingPosition(position) == (isInfiniteScrollingPosition == 1)

        where:
        position | dataCount | infiniteScrollingEnabled | bottomContentInset || isInfiniteScrollingPosition
        10       | 10        | 1                        | C.NO_VALUE         || 1
        10       | 10        | 1                        | 1                  || 1
        10       | 11        | 1                        | 1                  || 0
        10       | 10        | 0                        | C.NO_VALUE         || 0
        10       | 10        | 0                        | 1                  || 0
        10       | 11        | 0                        | 1                  || 0
    }
}

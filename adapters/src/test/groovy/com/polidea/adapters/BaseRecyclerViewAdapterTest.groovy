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

    def "GetItemId"() {

    }

    def "GetItemCount"() {

    }

    def "GetInfiniteScrollingLayoutResId"() {

    }

    def "GetTopContentInsetViewLayoutResId"() {

    }

    def "GetBottomContentInsetViewLayoutResId"() {

    }

    def "NotifyDataChanged"() {

    }

    def "NotifyDataRangeChanged"() {

    }

    def "NotifyDataInserted"() {

    }

    def "NotifyDataMoved"() {

    }

    def "NotifyDataRangeInserted"() {

    }

    def "NotifyDataRemoved"() {

    }

    def "NotifyDataRangeRemoved"() {

    }

    def "CreateInfiniteScrollingHolder"() {

    }

    def "BindInfiniteScrollingHolder"() {

    }

    def "ConfigureFullSpanIfNeeded"() {

    }

    def "GetDataItemViewId"() {

    }

    def "IsInfiniteScrollingPosition"() {

    }

    def "IsTopContentInsetViewPosition"() {

    }

    def "IsBottomContentInsetViewPosition"() {

    }

    def "IsFullSpanItemPosition"() {

    }

    def "InternalConfigureFullSpan"() {

    }

    def "CreateContentInsetViewHolderForInset"() {

    }

    def "GetLastPosition"() {

    }
}

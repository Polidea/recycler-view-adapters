package com.polidea.adapters

import spock.lang.Specification
import spock.lang.Unroll

class BaseSectionRecyclerViewAdapterTest extends Specification {

    def adapter = new SectionRecyclerViewAdapter()

    @Unroll
    def "get IndexPath for data position #dataPosition should equal IndexPath (section: #section, row: #row)"() {
        setup:
        adapter.sectionCount = 3
        adapter.rowCountMap.put(0, 3)
        adapter.rowCountMap.put(1, 2)
        adapter.rowCountMap.put(2, 1)
        adapter.hasSectionHeaderMap.put(0, true)
        adapter.hasSectionHeaderMap.put(1, false)
        adapter.hasSectionHeaderMap.put(2, true)

        when:
        def indexPath = adapter.getIndexPathForDataPosition(dataPosition)

        then:
        indexPath.getRow() == row
        indexPath.getSection() == section

        where:
        dataPosition | section | row
        0            | 0       | IndexPath.SECTION_HEADER
        1            | 0       | 0
        2            | 0       | 1
        3            | 0       | 2
        4            | 1       | 0
        5            | 1       | 1
        6            | 2       | IndexPath.SECTION_HEADER
        7            | 2       | 0
    }

    @Unroll
    def "get data position for IndexPath(section: #section, row: #row) should equal #dataPosition"() {
        setup:
        adapter.sectionCount = 3
        adapter.rowCountMap.put(0, 3)
        adapter.rowCountMap.put(1, 2)
        adapter.rowCountMap.put(2, 1)
        adapter.hasSectionHeaderMap.put(0, true)
        adapter.hasSectionHeaderMap.put(1, false)
        adapter.hasSectionHeaderMap.put(2, true)

        and:
        def indexPath = new IndexPath(section, row)

        expect:
        adapter.getDataPositionForIndexPath(indexPath) == dataPosition

        where:
        section | row                      || dataPosition
        0       | IndexPath.SECTION_HEADER || 0
        0       | 0                        || 1
        0       | 1                        || 2
        0       | 2                        || 3
        1       | 0                        || 4
        1       | 1                        || 5
        2       | IndexPath.SECTION_HEADER || 6
        2       | 0                        || 7
    }

    @Unroll
    def "get data count with one section (rowCount: #rowCount, hasHeader: #hasHeader) should equal #dataCount"() {
        setup:
        adapter.sectionCount = 1
        adapter.rowCountMap.put(0, rowCount)
        adapter.hasSectionHeaderMap.put(0, hasHeader == 1)

        expect:
        adapter.getDataCount() == dataCount

        where:
        hasHeader | rowCount || dataCount
        0         | 0        || 0
        1         | 0        || 1
        0         | 5        || 5
        1         | 5        || 6
    }

    def "get data count with many sections"() {
        setup:
        adapter.sectionCount = 5
        adapter.rowCountMap.put(0, 3)
        adapter.rowCountMap.put(1, 2)
        adapter.rowCountMap.put(2, 1)
        adapter.rowCountMap.put(3, 5)
        adapter.rowCountMap.put(4, 25)
        adapter.hasSectionHeaderMap.put(0, true)
        adapter.hasSectionHeaderMap.put(1, false)
        adapter.hasSectionHeaderMap.put(2, true)
        adapter.hasSectionHeaderMap.put(3, false)
        adapter.hasSectionHeaderMap.put(4, false)

        expect:
        adapter.getDataCount() == 38

    }

    @Unroll
    def "onBindDataViewHolder should call onBindSectionHeaderViewHolder when data position (#dataPosition) is section (#section)"() {
        setup:
        adapter.sectionCount = 2
        adapter.rowCountMap.put(0, 3)
        adapter.rowCountMap.put(1, 2)
        adapter.hasSectionHeaderMap.put(0, true)
        adapter.hasSectionHeaderMap.put(1, true)

        when:
        adapter.onBindDataViewHolder(null, dataPosition)

        then:
        adapter.onBindSectionHeaderViewHolderSectionList.size() == 1
        adapter.onBindSectionHeaderViewHolderSectionList.get(0) == section

        where:
        dataPosition || section
        0            || 0
        4            || 1
    }

    @Unroll
    def "onBindDataViewHolder should call onBindRowViewHolder when data position (#dataPosition) is row (section: #section, row: #row)"() {
        setup:
        adapter.sectionCount = 2
        adapter.rowCountMap.put(0, 3)
        adapter.rowCountMap.put(1, 2)
        adapter.hasSectionHeaderMap.put(0, true)
        adapter.hasSectionHeaderMap.put(1, true)

        when:
        adapter.onBindDataViewHolder(null, dataPosition)

        then:
        adapter.onBindRowViewHolderIndexPathList.size() == 1
        adapter.onBindRowViewHolderIndexPathList.get(0).getSection() == section
        adapter.onBindRowViewHolderIndexPathList.get(0).getRow() == row

        where:
        dataPosition | section | row
        1            | 0       | 0
        2            | 0       | 1
        3            | 0       | 2
        5            | 1       | 0
        6            | 1       | 1
    }

    @Unroll
    def "getDataItemViewId should call getSectionItemViewId and return (#id) when dataPosition (#dataPosition) is section"() {
        setup:
        adapter.sectionCount = 2
        adapter.rowCountMap.put(0, 3)
        adapter.rowCountMap.put(1, 2)
        adapter.hasSectionHeaderMap.put(0, true)
        adapter.hasSectionHeaderMap.put(1, true)

        and:
        adapter.sectionItemViewIdMap.put(0, 0)
        adapter.sectionItemViewIdMap.put(1, 1)

        expect:
        adapter.getDataItemViewId(dataPosition) == id

        where:
        dataPosition || id
        0            || 0
        4            || 1
    }

    @Unroll
    def "getDataItemViewId should call getRowItemViewId when dataPosition (#dataPosition) is row (section: #section, row: #row)"() {
        setup:
        adapter.sectionCount = 2
        adapter.rowCountMap.put(0, 3)
        adapter.rowCountMap.put(1, 2)
        adapter.hasSectionHeaderMap.put(0, true)
        adapter.hasSectionHeaderMap.put(1, true)

        and:
        adapter.rowItemViewIdMap.put(new IndexPath(0, 0), 0)
        adapter.rowItemViewIdMap.put(new IndexPath(0, 1), 1)
        adapter.rowItemViewIdMap.put(new IndexPath(0, 2), 2)
        adapter.rowItemViewIdMap.put(new IndexPath(1, 0), 3)
        adapter.rowItemViewIdMap.put(new IndexPath(1, 1), 4)

        expect:
        adapter.getDataItemViewId(dataPosition) == id

        where:
        dataPosition || id
        1            || 0
        2            || 1
        3            || 2
        5            || 3
        6            || 4
    }
}

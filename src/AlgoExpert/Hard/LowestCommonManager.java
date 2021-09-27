package AlgoExpert.Hard;

import java.util.*;
public class LowestCommonManager {
    /**
     * Trick is to return the number of important reports and the lowest common manager
     * at any node.
     */
    //O(n) time | O(d) space
    static OrgChart getLowestCommonManager (OrgChart topManager,
                                            OrgChart reportOne,
                                            OrgChart reportTwo) {
        return getOrgInfo(topManager, reportOne, reportTwo).lowestCommonManager;
    }

    /**
     * 1. initialize the count to 0
     * 2. iterate through all direct reports and recurse, if the returned object is not null
     * we have found the lowest common manager (2 reports). Else add to existing num reports
     * 3. base case: if reportOne or reportTwo is found add to count
     * 4. return object with count and node
     * The idea is to pass the Org information up the tree from the leaves and when we add up to
     * two direct report return from method immediately, where we obtain the number of direct reports
     * and the lowest manager node
     */
    static OrgInfo getOrgInfo(OrgChart manager,
                                    OrgChart reportOne,
                                    OrgChart reportTwo) {
        // keep track of the numImportantReports using a local variable
        // and pass it down the recursion chain
        int numImportantReports = 0;

        // base case
        if (manager == reportOne || manager == reportTwo) numImportantReports++;

        // get the number of important reports by recursion
        for (OrgChart directReport : manager.directReports) {
            // recurse with each directReport as manager
            OrgInfo orgInfoFromRecursion = getOrgInfo(directReport, reportOne, reportTwo);
            // if lowest common manager is not null, means there are 2 important reports already
            // just return
            if (orgInfoFromRecursion.lowestCommonManager != null) return orgInfoFromRecursion;
            // else add up the number of important reports obtained from recusion
            // NOTE that the numImportantReports is updated twice!!
            // numImportantReports is updated from the current node as well
            // as added to the cumulative number numImportantReports
            numImportantReports += orgInfoFromRecursion.numImportantReports;
        }

        // lowest common manager is the manager if number important reports has reached 2
        // return null otherwise
        // note that OrgChart is only returned when the two important reports are found
        // else the OrgChart object is null, "we only need to keep track of the count"
        OrgChart lowestCommonMgr;
        if (numImportantReports == 2) {
            lowestCommonMgr = manager;
        } else {
            lowestCommonMgr = null; // return NULL if reports hasn't reached 2
        }

        // feed the numImportantReports and lowestCommonMgr
        // (might be null or found) to next recursion
        OrgInfo newOrgInfo = new OrgInfo(lowestCommonMgr, numImportantReports);
        return newOrgInfo;
    }

    /**
     * My Solution
     */
    static OrgInfo findLowestMySol(OrgChart topMgr, OrgChart one, OrgChart two) {
        if (topMgr == null) return new OrgInfo(null, 0);

        int numberReports = 0;
        if (topMgr == one || topMgr == two) {
            numberReports ++;
        }
        for (OrgChart directReport : topMgr.directReports) {
            OrgInfo curOrgChart = findLowestMySol(directReport, one, two);
            if (curOrgChart.lowestCommonManager != null) return curOrgChart;
            numberReports += curOrgChart.numImportantReports;
        }

        OrgInfo orgInfo = new OrgInfo(null, numberReports);
        if (numberReports == 2) {
            orgInfo.lowestCommonManager = topMgr;
            orgInfo.numImportantReports = 2;
            return orgInfo;
        }
        return orgInfo;
    }

    static class OrgInfo {
        public OrgChart lowestCommonManager;
        int numImportantReports;

        OrgInfo(OrgChart lowestCommonManager, int numImportantReports) {
            this.lowestCommonManager = lowestCommonManager;
            this.numImportantReports = numImportantReports;
        }
    }

    static class OrgChart {
        public char name;
        public List<OrgChart> directReports;

        OrgChart(char name) {
            this.name = name;
            this.directReports = new ArrayList<>();
        }
    }

}

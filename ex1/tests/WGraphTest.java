package ex1.tests;

import ex1.src.WGraph_DS;
import ex1.src.node_info;
import ex1.src.weighted_graph;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
public class WGraphTest {
    @Test
    void removeNodeAndEdges() {
        weighted_graph g0 = small_graph_creator();
        g0.removeNode(3);
        g0.removeEdge(1, 0);
        boolean flag = true;
        for (node_info curr : g0.getV()) {
            for (node_info other : g0.getV(curr.getKey())) {
                if (other.getKey() == 3)
                    flag = false;
            }
        }
        Assertions.assertEquals(1, g0.edgeSize());//test for number of edges after removing node
        Assertions.assertEquals(true, flag);
        Assertions.assertEquals(4, g0.nodeSize());
    }

    @Test
    void AddAndRemoveDoubleNodesAndEdges() {
        weighted_graph g0 = small_graph_creator();
        double i = 0.3;
        g0.connect(0, 1, i);//same
        g0.connect(1, 2, i);//same
        Assertions.assertEquals(5, g0.nodeSize());
        Assertions.assertEquals(5, g0.edgeSize());
        g0.removeEdge(0, 1);
        g0.removeEdge(0, 1);
        g0.removeNode(2);
        g0.removeNode(2);
        Assertions.assertEquals(2, g0.edgeSize());
        Assertions.assertEquals(4, g0.nodeSize());
    }

    @Test
    void checkGetAndHasEdgeFunctions() {
        weighted_graph g0 = small_graph_creator();
        Assertions.assertEquals(0, g0.getEdge(1, 1));
        Assertions.assertEquals(-1, g0.getEdge(1, 7));
        g0.connect(1, 3, 12);//check update from 0.3->12
        Assertions.assertEquals(12, g0.getEdge(1, 3));
    }

    @Test
    void checkMC() {
        weighted_graph g0 = small_graph_creator();
        Assertions.assertEquals(10, g0.getMC());
        g0.removeEdge(3, 4);
        Assertions.assertEquals(11, g0.getMC());//check if mc updates when removing edge
        g0.removeNode(0);//check if mc updates when removing Node with edges
        Assertions.assertEquals(14, g0.getMC());
        g0.connect(1, 3, 0.2);////check if mc updates when connecting between two nodes
        Assertions.assertEquals(15, g0.getMC());
        g0.connect(1, 3, 0.6);//check if mc updates while changing weight
        Assertions.assertEquals(16, g0.getMC());
        g0.connect(1, 3, 0.6);//check if mc does not update while changing to the same weight
        Assertions.assertEquals(16, g0.getMC());
    }

    @Test
    void getV() {
        weighted_graph g0 = small_graph_creator();
        Assertions.assertEquals(g0.edgeSize(), g0.getV().size());//check size
        Iterator<node_info> iterator = g0.getV().iterator();
        int[] nodes = nodes(g0);
        boolean flag = true;
        int i = 0;
        for (int node : nodes) {
            while (iterator.hasNext()) {
                if (iterator.next().getKey() == node)
                    i++;
            }
            iterator = g0.getV().iterator();
        }
        Assertions.assertEquals(g0.nodeSize(), i);//check nodes
        for (int node : nodes) {
            for (node_info currNi : g0.getV(node)) {
                if (!g0.hasEdge(node, currNi.getKey())) {
                    flag = false;
                }
            }
            Assertions.assertTrue(flag);//check Ni
        }
    }
    //////////////////////////////////////////////////////

    public static weighted_graph small_graph_creator() {
        weighted_graph g0 = new WGraph_DS();
        for (int i = 0; i < 5; i++) {
            g0.addNode(i);
        }
        double i = 0.3;
        g0.connect(0, 1, i);
        g0.connect(1, 2, i);
        g0.connect(0, 3, i);
        g0.connect(4, 3, i);
        g0.connect(2, 3, i);
        return g0;
    }

    private static int[] nodes(weighted_graph g) {
        int size = g.nodeSize();
        Collection<node_info> V = g.getV();
        node_info[] nodes = new node_info[size];
        V.toArray(nodes);
        int[] arr = new int[size];
        int i = 0;
        for (node_info Node : nodes) {
            arr[i] = Node.getKey();
            i++;
        }
        return arr;
    }
}

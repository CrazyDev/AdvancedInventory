/*
 * Copyright (C) 2018 Hugo
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.gmail.hugosilvaf2.gui.lib.sections;

import com.gmail.hugosilvaf2.gui.lib.GUI;
import com.gmail.hugosilvaf2.gui.lib.Pages.Page;
import java.util.Arrays;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class Section {

    private GUI gui;
    private Player viewer;
    private Inventory inventory;
    private int nowPage;

    public Section(GUI gui, Player viewer, Inventory inventory) {
        this.gui        = gui;
        this.viewer     = viewer;
        this.inventory  = inventory;

       this. nowPage    = 0;
    }

    /**
     * Obtém o GUI, lembre-se que o GUI não será a página atual que o jogador
     * está visualuzando
     *
     * @return
     */
    public GUI getGui() {
        return gui;
    }

    /**
     * Obtém o inventário da página atual em que o jogador está visualizandoF
     *
     * @return
     */
    public Inventory getInventory() {
        return inventory;
    }

    /**
     * Obtém a página atual que jogador está visualizando
     *
     * @return
     */
    public Page getNowPage() {
        return (Page) gui.get(nowPage);
    }

    /**
     * Obtém a última página que o jogador visualizou
     *
     * @return
     */
    public Page getLastPage() {
        return (Page) gui.get(getLastPageInt());
    }

    /**
     * Obtém o index da página atual
     *
     * @return
     */
    public int getNowPageInt() {
        return nowPage;
    }

    /**
     * Obtém o index da última página que o jogador visualizou
     *
     * @return
     */
    public int getLastPageInt() {
        return nowPage >= 0 ? nowPage - 1 : 0;
    }

    /**
     * Compara com outro inventário
     *
     * @param i
     * @return
     */
    public boolean compareTo(Inventory i) {
        return ((inventory.getName().equals(i.getName())) && (Arrays.stream(inventory.getContents()).filter(a -> Arrays.stream(i.getContents()).filter(b -> a.equals(b)).findFirst().isPresent()).findFirst().isPresent()));
    }

    /**
     * Obtém o jogador desta seção, que está visualizando está página
     *
     * @return
     */
    public Player getViewer() {
        return viewer;
    }

    /**
     * Seta a página atual que o jogador irá visualizar
     *
     * @param nowPage
     */
    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    /**
     * Vai para próxima pagina
     */
    public void nextPage() {
        if (gui.size() > nowPage + 1) {
            nowPage += 1;
            updateInventory();
        }
    }

    /**
     * Volta para página anterior
     */
    public void previousPage() {
        if (nowPage - 1 >= 0) {
            nowPage -= 1;
            updateInventory();
        }
    }

    /**
     * Atualiza o inventário do jogador
     */
    public void updateInventory() {
        inventory.clear();
        Page page = (Page) getGui().get(nowPage);
        for (int i = 0; i < page.size(); i++) {
            if (page.get(i) != null) {
                inventory.setItem(i, page.get(i).getIcon());
            }
        }
        viewer.updateInventory();
    }
}

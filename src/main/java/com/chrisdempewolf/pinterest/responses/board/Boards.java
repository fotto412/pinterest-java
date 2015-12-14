package com.chrisdempewolf.pinterest.responses.board;

import com.chrisdempewolf.pinterest.responses.Page;
import com.google.gson.annotations.SerializedName;

import java.util.Arrays;
import java.util.List;

public class Boards {
    @SerializedName("data")
    private final List<Board> boards;
    private final Page page;

    public Boards(Board[] boards, Page page) {
        this.boards = Arrays.asList(boards);
        this.page = page;
    }

    public List<Board> getBoards() {
        return boards;
    }

    public Page getPage() {
        return page;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Boards boards1 = (Boards) o;

        if (boards != null ? !boards.equals(boards1.boards) : boards1.boards != null) return false;
        return page != null ? page.equals(boards1.page) : boards1.page == null;

    }

    @Override
    public int hashCode() {
        int result = boards != null ? boards.hashCode() : 0;
        result = 31 * result + (page != null ? page.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Boards{" +
                "boards=" + boards +
                ", page=" + page +
                '}';
    }
}

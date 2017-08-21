package com.bship.games.endpoints.cabinet.entity

import com.bship.games.Configuration.PieceDeserializer
import com.bship.games.logic.definitions.Orientation
import com.bship.games.logic.definitions.Orientation.Dummy.INVALID_ORIENTATION
import com.bship.games.logic.definitions.PieceType
import com.bship.games.logic.definitions.PieceType.Dummy.INVALID_PIECE
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import java.util.*

@JsonDeserialize(using = PieceDeserializer::class)
class Piece private constructor(val type: PieceType,
                                val id: Long?,
                                val boardId: Long?,
                                val taken: Boolean,
                                val placement: Point,
                                val orientation: Orientation) {

    private constructor(builder: Builder) : this(
            type = builder.type.orElse(INVALID_PIECE),
            placement = builder.placement.orElse(Point()),
            orientation = builder.orientation.orElse(INVALID_ORIENTATION),
            boardId = builder.boardId.orElse(null),
            id = builder.id.orElse(null),
            taken = builder.taken.orElse(false)
    )

    fun copy(init: Builder.() -> Unit): Piece =
            Builder(this).apply(init).build()

    class Builder constructor() {
        internal constructor(piece: Piece) : this() {
            withType { piece.type }
            withBoardId { piece.boardId }
            withId { piece.id }
            withTaken { piece.taken }
            withPlacement { piece.placement }
            withOrientation { piece.orientation }
        }

        internal var type: Optional<PieceType> = Optional.empty(); private set
        internal var boardId: Optional<Long> = Optional.empty(); private set
        internal var id: Optional<Long> = Optional.empty(); private set
        internal var taken: Optional<Boolean> = Optional.empty(); private set
        internal var placement: Optional<Point> = Optional.empty(); private set
        internal var orientation: Optional<Orientation> = Optional.empty(); private set

        fun withType(init: Builder.() -> PieceType?) = apply {
            type = Optional.ofNullable(init())
        }

        fun withPlacement(init: Builder.() -> Point?) = apply {
            placement = Optional.ofNullable(init())
        }

        fun withOrientation(init: Builder.() -> Orientation?) = apply {
            orientation = Optional.ofNullable(init())
        }

        fun withBoardId(init: Builder.() -> Long?) = apply {
            boardId = Optional.ofNullable(init())
        }

        fun withId(init: Builder.() -> Long?) = apply {
            id = Optional.ofNullable(init())
        }

        fun withTaken(init: Builder.() -> Boolean) = apply {
            taken = Optional.ofNullable(init())
        }

        fun build(): Piece = Piece(this)
    }

    override fun toString(): String {
        return StringJoiner(", ", "{", "}")
                .add("\"id\": $id")
                .add("\"boardId\": $boardId")
                .add("\"placement\": $placement")
                .add("\"orientation\": $orientation")
                .add("\"taken\": $taken")
                .add("\"type\": $type")
                .toString()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Piece

        if (type != other.type) return false
        if (boardId != other.boardId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = type.hashCode()
        result = 31 * result + (boardId?.hashCode() ?: 0)
        return result
    }

    companion object {
        inline fun build(init: Builder.() -> Unit): Piece =
                Builder().apply(init).build()
    }
}

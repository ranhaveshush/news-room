package com.example.baseapp.vo

/**
 * Common class used by repositories to represent a returned [resource state][State] and
 * in case of a success resource also it's typed data.
 */
sealed class Resource<out T> {
    abstract val state: State
    abstract val data: T?

    /**
     * Resource current status.
     */
    enum class Status {
        LOADING,
        SUCCESS,
        FAILURE
    }

    /**
     * The resource state comprised of the resource current [Status]
     * and in case of an error the error message and the error [cause][Throwable].
     */
    sealed class State {
        abstract val status: Status
        abstract val message: String?

        data class LoadingState(
            override val status: Status,
            override val message: String? = null
        ) : State()
        data class SuccessState(
            override val status: Status,
            override val message: String? = null
        ) : State()
        data class ErrorState(
            override val status: Status,
            override val message: String,
            val cause: Throwable? = null
        ) : State()

        companion object {
            val LOADING = LoadingState(Status.LOADING)
            val SUCCESS = SuccessState(Status.SUCCESS)

            fun error(message: String, cause: Throwable? = null) = ErrorState(
                Status.FAILURE,
                message,
                cause
            )
        }
    }

    data class LoadingResource<T>(
        override val state: State.LoadingState,
        override val data: T? = null
    ) : Resource<T>()
    data class EmptyResource<T>(
        override val state: State.SuccessState,
        override val data: T? = null
    ) : Resource<T>()
    data class SuccessResource<T>(
        override val state: State.SuccessState,
        override val data: T
    ) : Resource<T>()
    data class ErrorResource<T>(
        override val state: State.ErrorState,
        override val data: T? = null
    ) : Resource<T>()

    companion object {
        /**
         * Convenient method to build a loading resource.
         */
        fun <T> loading() = LoadingResource<T>(State.LOADING)

        /**
         * Convenient method to build an empty success resource.
         */
        fun <T> empty() = EmptyResource<T>(State.SUCCESS)

        /**
         * Convenient method to build a success resource with a given data.
         *
         * @param data The data held by the resource.
         */
        fun <T> success(data: T) = SuccessResource(State.SUCCESS, data)

        /**
         * Convenient method to build a failure resource with a given error message and
         * optionally a [cause][Throwable].
         *
         * @param message The failure error message.
         * @param cause The failure error throwable cause, if exists.
         */
        fun <T> error(message: String, cause: Throwable? = null) = ErrorResource<T>(
            State.error(
                message,
                cause
            )
        )
    }
}
